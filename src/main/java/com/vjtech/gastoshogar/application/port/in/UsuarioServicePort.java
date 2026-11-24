package com.vjtech.gastoshogar.application.port.in;

import com.vjtech.gastoshogar.adapter.rest.dto.usuario.UsuarioRequest;
import com.vjtech.gastoshogar.adapter.rest.dto.usuario.UsuarioResponse;
import com.vjtech.gastoshogar.adapter.rest.dto.usuario.UsuarioUpdateRequest;

import java.util.List;

public interface UsuarioServicePort {
    UsuarioResponse create(UsuarioRequest req);
    List<UsuarioResponse> findAll();
    UsuarioResponse findById(Long idUser);
    UsuarioResponse findByUser(String user);
    UsuarioResponse update(Long idUser, UsuarioUpdateRequest req);
    void delete(Long idUser);
}