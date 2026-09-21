package com.vjtech.gastoshogar.adapter.rest.dto.auth;

import java.util.UUID;

public class UserResponse {
    private UUID id;
    private String email;
    private String nombre;

    public UserResponse() {}

    public UserResponse(UUID id, String email, String nombre) {
        this.id = id;
        this.email = email;
        this.nombre = nombre;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}