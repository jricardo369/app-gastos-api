package com.vjtech.gastoshogar.application.service;

import com.vjtech.gastoshogar.adapter.persistence.entity.MembresiaEntity;
import com.vjtech.gastoshogar.adapter.persistence.entity.UsuarioEntity;
import com.vjtech.gastoshogar.adapter.persistence.repository.MembresiaRepository;
import com.vjtech.gastoshogar.adapter.persistence.repository.UsuarioRepository;
import com.vjtech.gastoshogar.adapter.rest.dto.membresia.MembresiaRequest;
import com.vjtech.gastoshogar.adapter.rest.dto.membresia.MembresiaResponse;
import com.vjtech.gastoshogar.application.port.in.MembresiaServicePort;
import com.vjtech.gastoshogar.common.exception.ResourceNotFoundException;
import com.vjtech.gastoshogar.common.exception.ValidationException;
import com.vjtech.gastoshogar.domain.enums.EstadoMembresia;
import com.vjtech.gastoshogar.domain.enums.TipoMembresia;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MembresiaServiceImpl implements MembresiaServicePort {
    private final MembresiaRepository membresiaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;

    public MembresiaServiceImpl(MembresiaRepository membresiaRepository, UsuarioRepository usuarioRepository, ModelMapper modelMapper) {
        this.membresiaRepository = membresiaRepository;
        this.usuarioRepository = usuarioRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public MembresiaResponse create(MembresiaRequest req) {
        TipoMembresia tipo = parseTipo(req.getType());
        EstadoMembresia estado = parseEstado(req.getEstado());
        LocalDate inicio = LocalDate.now();
        return crear(req.getIdUser(), tipo, estado, inicio);
    }

    @Override
    @Transactional
    public MembresiaResponse create(Long idUser, String type) {
        TipoMembresia tipo = parseTipo(type);
        return crear(idUser, tipo, EstadoMembresia.ACTIVA, LocalDate.now());
    }

    private MembresiaResponse crear(Long idUser, TipoMembresia tipo, EstadoMembresia estado, LocalDate inicio) {
        if (idUser == null) {
            throw new ValidationException("El campo idUser es obligatorio");
        }
        UsuarioEntity usuario = usuarioRepository.findById(idUser)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + idUser));

        MembresiaEntity entity = new MembresiaEntity();
        entity.setIdUser(usuario.getIdUser());
        entity.setType(tipo.name());
        entity.setFechaInicio(inicio);
        entity.setFechaFin(calcularFin(inicio, tipo));
        entity.setEstado(estado.name());
        return modelMapper.map(membresiaRepository.save(entity), MembresiaResponse.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MembresiaResponse> findByUsuario(Long idUser) {
        if (!usuarioRepository.existsById(idUser)) {
            throw new ResourceNotFoundException("Usuario no encontrado: " + idUser);
        }
        return membresiaRepository.findByIdUserOrderByFechaInicioDesc(idUser).stream()
                .map(e -> modelMapper.map(e, MembresiaResponse.class))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public MembresiaResponse findById(Long id) {
        return modelMapper.map(getEntity(id), MembresiaResponse.class);
    }

    @Override
    @Transactional
    public MembresiaResponse update(Long id, MembresiaRequest req) {
        MembresiaEntity entity = getEntity(id);
        TipoMembresia tipo = parseTipo(req.getType() != null ? req.getType() : entity.getType());
        entity.setType(tipo.name());
        entity.setFechaFin(calcularFin(entity.getFechaInicio(), tipo));
        if (req.getEstado() != null) {
            entity.setEstado(parseEstado(req.getEstado()).name());
        }
        return modelMapper.map(membresiaRepository.save(entity), MembresiaResponse.class);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        MembresiaEntity entity = getEntity(id);
        membresiaRepository.delete(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MembresiaResponse> findActiva(Long idUser) {
        LocalDate hoy = LocalDate.now();
        return membresiaRepository
                .findFirstByIdUserAndEstadoAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqualOrderByFechaInicioDesc(
                        idUser, EstadoMembresia.ACTIVA.name(), hoy, hoy)
                .map(e -> modelMapper.map(e, MembresiaResponse.class));
    }

    private MembresiaEntity getEntity(Long id) {
        return membresiaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Membresía no encontrada: " + id));
    }

    private LocalDate calcularFin(LocalDate inicio, TipoMembresia tipo) {
        return inicio.plusMonths(tipo.mesesDeDuracion());
    }

    private TipoMembresia parseTipo(String value) {
        try {
            TipoMembresia tipo = TipoMembresia.from(value);
            if (tipo == null) {
                throw new IllegalArgumentException("vacío");
            }
            return tipo;
        } catch (IllegalArgumentException e) {
            throw new ValidationException(e.getMessage());
        }
    }

    private EstadoMembresia parseEstado(String value) {
        if (value == null || value.isBlank()) {
            return EstadoMembresia.ACTIVA;
        }
        try {
            return EstadoMembresia.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Estado de membresía inválido: " + value + ". Valores permitidos: ACTIVA, INACTIVA");
        }
    }
}