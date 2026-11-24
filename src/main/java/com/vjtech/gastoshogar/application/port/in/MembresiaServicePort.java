package com.vjtech.gastoshogar.application.port.in;

import com.vjtech.gastoshogar.adapter.rest.dto.membresia.MembresiaRequest;
import com.vjtech.gastoshogar.adapter.rest.dto.membresia.MembresiaResponse;

import java.util.List;
import java.util.Optional;

public interface MembresiaServicePort {
    MembresiaResponse create(MembresiaRequest req);
    MembresiaResponse create(Long idUser, String type);
    List<MembresiaResponse> findByUsuario(Long idUser);
    MembresiaResponse findById(Long id);
    MembresiaResponse update(Long id, MembresiaRequest req);
    void delete(Long id);
    Optional<MembresiaResponse> findActiva(Long idUser);
}