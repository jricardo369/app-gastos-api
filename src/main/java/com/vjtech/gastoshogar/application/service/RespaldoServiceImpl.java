package com.vjtech.gastoshogar.application.service;

import com.vjtech.gastoshogar.adapter.persistence.entity.RespaldoEntity;
import com.vjtech.gastoshogar.adapter.persistence.entity.UsuarioEntity;
import com.vjtech.gastoshogar.adapter.persistence.repository.RespaldoRepository;
import com.vjtech.gastoshogar.adapter.persistence.repository.UsuarioRepository;
import com.vjtech.gastoshogar.adapter.rest.dto.respaldo.RespaldoRequest;
import com.vjtech.gastoshogar.adapter.rest.dto.respaldo.RespaldoResponse;
import com.vjtech.gastoshogar.application.port.in.MembresiaServicePort;
import com.vjtech.gastoshogar.application.port.in.RespaldoServicePort;
import com.vjtech.gastoshogar.common.exception.ResourceNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RespaldoServiceImpl implements RespaldoServicePort {
    private final RespaldoRepository respaldoRepository;
    private final UsuarioRepository usuarioRepository;
    private final MembresiaServicePort membresiaServicePort;

    public RespaldoServiceImpl(RespaldoRepository respaldoRepository,
                               UsuarioRepository usuarioRepository,
                               MembresiaServicePort membresiaServicePort) {
        this.respaldoRepository = respaldoRepository;
        this.usuarioRepository = usuarioRepository;
        this.membresiaServicePort = membresiaServicePort;
    }

    @Override
    @Transactional
    public RespaldoResponse guardar(String username, RespaldoRequest req) {
        Long idUser = usuarioConMembresiaActiva(username);
        RespaldoEntity respaldo = respaldoRepository.findByIdUser(idUser).orElseGet(RespaldoEntity::new);
        respaldo.setIdUser(idUser);
        respaldo.setSchemaVersion(req.getSchemaVersion());
        respaldo.setPayloadJson(req.getPayload());
        return toResponse(respaldoRepository.save(respaldo), false);
    }

    @Override
    @Transactional(readOnly = true)
    public RespaldoResponse obtener(String username) {
        Long idUser = usuarioConMembresiaActiva(username);
        RespaldoEntity respaldo = respaldoRepository.findByIdUser(idUser)
                .orElseThrow(() -> new ResourceNotFoundException("No hay respaldo guardado para este usuario"));
        return toResponse(respaldo, true);
    }

    private Long usuarioConMembresiaActiva(String username) {
        UsuarioEntity usuario = usuarioRepository.findByUser(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + username));
        if (membresiaServicePort.findActiva(usuario.getIdUser()).isEmpty()) {
            throw new AccessDeniedException("La membresía del usuario no está activa");
        }
        return usuario.getIdUser();
    }

    private RespaldoResponse toResponse(RespaldoEntity respaldo, boolean incluirPayload) {
        RespaldoResponse res = new RespaldoResponse();
        res.setId(respaldo.getId());
        res.setSchemaVersion(respaldo.getSchemaVersion());
        res.setUpdatedAt(respaldo.getUpdatedAt());
        if (incluirPayload) {
            res.setPayload(respaldo.getPayloadJson());
        }
        return res;
    }
}
