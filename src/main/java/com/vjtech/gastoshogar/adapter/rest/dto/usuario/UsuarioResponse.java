package com.vjtech.gastoshogar.adapter.rest.dto.usuario;

import java.time.Instant;

public class UsuarioResponse {
    private Long idUser;
    private String user;
    private String name;
    private String address;
    private String phone;
    private String email;
    private Instant createdAt;
    private Instant updatedAt;

    public UsuarioResponse() {}

    public Long getIdUser() { return idUser; }
    public void setIdUser(Long idUser) { this.idUser = idUser; }
    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}