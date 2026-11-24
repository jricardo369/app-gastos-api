package com.vjtech.gastoshogar.adapter.rest.dto.auth;

import com.vjtech.gastoshogar.adapter.rest.dto.usuario.UsuarioRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest extends UsuarioRequest {
    @NotBlank @Size(max = 10) private String membresiaType;

    public RegisterRequest() {}

    public String getMembresiaType() { return membresiaType; }
    public void setMembresiaType(String membresiaType) { this.membresiaType = membresiaType; }
}