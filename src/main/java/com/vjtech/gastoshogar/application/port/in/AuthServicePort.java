package com.vjtech.gastoshogar.application.port.in;

import com.vjtech.gastoshogar.adapter.rest.dto.auth.LoginRequest;
import com.vjtech.gastoshogar.adapter.rest.dto.auth.RegisterRequest;
import com.vjtech.gastoshogar.adapter.rest.dto.auth.AuthResponse;
import com.vjtech.gastoshogar.adapter.rest.dto.usuario.UsuarioResponse;


public interface AuthServicePort {
    AuthResponse register(RegisterRequest req);
    AuthResponse login(LoginRequest req);
    AuthResponse getMe(String username);
    UsuarioResponse findUsuario(Long idUser);
}