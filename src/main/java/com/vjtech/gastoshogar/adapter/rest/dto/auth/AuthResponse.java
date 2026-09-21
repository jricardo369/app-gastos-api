package com.vjtech.gastoshogar.adapter.rest.dto.auth;

import com.vjtech.gastoshogar.adapter.rest.dto.auth.UserResponse;

public class AuthResponse {
    private String token;
    private String tokenType;
    private UserResponse usuario;

    public AuthResponse() {}

    public AuthResponse(String token, String tokenType, UserResponse usuario) {
        this.token = token;
        this.tokenType = tokenType;
        this.usuario = usuario;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getTokenType() { return tokenType; }
    public void setTokenType(String tokenType) { this.tokenType = tokenType; }
    public UserResponse getUsuario() { return usuario; }
    public void setUsuario(UserResponse usuario) { this.usuario = usuario; }
}