package com.vjtech.gastoshogar.adapter.rest.dto.membresia;

import java.time.Instant;
import java.time.LocalDate;

public class MembresiaResponse {
    private Long id;
    private Long idUser;
    private String type;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estado;
    private Instant createdAt;
    private Instant updatedAt;

    public MembresiaResponse() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getIdUser() { return idUser; }
    public void setIdUser(Long idUser) { this.idUser = idUser; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}