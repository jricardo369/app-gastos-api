package com.vjtech.gastoshogar.adapter.rest.dto.membresia;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public class MembresiaRequest {
    private Long idUser;

    @NotBlank @Size(max = 10) private String type;

    @Size(max = 20) private String estado;

    public MembresiaRequest() {}

    public MembresiaRequest(Long idUser, String type, String estado) {
        this.idUser = idUser;
        this.type = type;
        this.estado = estado;
    }

    public Long getIdUser() { return idUser; }
    public void setIdUser(Long idUser) { this.idUser = idUser; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}