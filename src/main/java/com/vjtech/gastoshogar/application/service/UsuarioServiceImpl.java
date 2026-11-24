package com.vjtech.gastoshogar.application.service;

import com.vjtech.gastoshogar.adapter.persistence.entity.UsuarioEntity;
import com.vjtech.gastoshogar.adapter.persistence.repository.MembresiaRepository;
import com.vjtech.gastoshogar.adapter.persistence.repository.UsuarioRepository;
import com.vjtech.gastoshogar.adapter.rest.dto.usuario.UsuarioRequest;
import com.vjtech.gastoshogar.adapter.rest.dto.usuario.UsuarioResponse;
import com.vjtech.gastoshogar.adapter.rest.dto.usuario.UsuarioUpdateRequest;
import com.vjtech.gastoshogar.application.port.in.UsuarioServicePort;
import com.vjtech.gastoshogar.common.exception.ResourceNotFoundException;
import com.vjtech.gastoshogar.common.exception.ValidationException;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioServicePort {
    private final UsuarioRepository usuarioRepository;
    private final MembresiaRepository membresiaRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository,
                              MembresiaRepository membresiaRepository,
                              PasswordEncoder passwordEncoder,
                              ModelMapper modelMapper) {
        this.usuarioRepository = usuarioRepository;
        this.membresiaRepository = membresiaRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public UsuarioResponse create(UsuarioRequest req) {
        verificarDuplicados(req.getUser(), req.getEmail(), null);
        UsuarioEntity entity = new UsuarioEntity();
        entity.setUser(req.getUser().trim());
        entity.setEmail(req.getEmail().trim().toLowerCase());
        entity.setPassword(passwordEncoder.encode(req.getPassword()));
        entity.setName(req.getName());
        entity.setAddress(req.getAddress());
        entity.setPhone(req.getPhone());
        return modelMapper.map(usuarioRepository.save(entity), UsuarioResponse.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponse> findAll() {
        return usuarioRepository.findAll().stream()
                .map(e -> modelMapper.map(e, UsuarioResponse.class))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponse findById(Long idUser) {
        return modelMapper.map(getEntity(idUser), UsuarioResponse.class);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponse findByUser(String user) {
        return usuarioRepository.findByUser(user)
                .map(e -> modelMapper.map(e, UsuarioResponse.class))
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + user));
    }

    @Override
    @Transactional
    public UsuarioResponse update(Long idUser, UsuarioUpdateRequest req) {
        UsuarioEntity entity = getEntity(idUser);
        if (req.getUser() != null && !req.getUser().isBlank()) {
            entity.setUser(req.getUser().trim());
        }
        if (req.getEmail() != null && !req.getEmail().isBlank()) {
            entity.setEmail(req.getEmail().trim().toLowerCase());
        }
        verificarDuplicados(entity.getUser(), entity.getEmail(), idUser);
        if (req.getPassword() != null && !req.getPassword().isBlank()) {
            entity.setPassword(passwordEncoder.encode(req.getPassword()));
        }
        if (req.getName() != null) {
            entity.setName(req.getName());
        }
        if (req.getAddress() != null) {
            entity.setAddress(req.getAddress());
        }
        if (req.getPhone() != null) {
            entity.setPhone(req.getPhone());
        }
        return modelMapper.map(usuarioRepository.save(entity), UsuarioResponse.class);
    }

    @Override
    @Transactional
    public void delete(Long idUser) {
        UsuarioEntity entity = getEntity(idUser);
        membresiaRepository.deleteAll(membresiaRepository.findByIdUserOrderByFechaInicioDesc(idUser));
        usuarioRepository.delete(entity);
    }

    public UsuarioEntity getEntity(Long idUser) {
        return usuarioRepository.findById(idUser)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + idUser));
    }

    private void verificarDuplicados(String user, String email, Long excludeId) {
        usuarioRepository.findByUser(user)
                .filter(u -> excludeId == null || !u.getIdUser().equals(excludeId))
                .ifPresent(u -> { throw new ValidationException("El usuario '" + user + "' ya está registrado"); });
        usuarioRepository.findByEmail(email)
                .filter(u -> excludeId == null || !u.getIdUser().equals(excludeId))
                .ifPresent(u -> { throw new ValidationException("El email ya está registrado"); });
    }
}