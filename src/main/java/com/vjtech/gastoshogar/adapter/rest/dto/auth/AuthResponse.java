package com.vjtech.gastoshogar.adapter.rest.dto.auth;

import com.vjtech.gastoshogar.adapter.rest.dto.membresia.MembresiaResponse;
import com.vjtech.gastoshogar.adapter.rest.dto.usuario.UsuarioResponse;

public class AuthResponse {
    private String token;
    private String tokenType;
    private UsuarioResponse usuario;
    private MembresiaResponse membresia;
    private boolean membresiaActiva;

    public AuthResponse() {}

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getTokenType() { return tokenType; }
    public void setTokenType(String tokenType) { this.tokenType = tokenType; }
    public UsuarioResponse getUsuario() { return usuario; }
    public void setUsuario(UsuarioResponse usuario) { this.usuario = usuario; }
    public MembresiaResponse getMembresia() { return membresia; }
    public void setMembresia(MembresiaResponse membresia) { this.membresia = membresia; }
    public boolean isMembresiaActiva() { return membresiaActiva; }
    public void setMembresiaActiva(boolean membresiaActiva) { this.membresiaActiva = membresiaActiva; }
}