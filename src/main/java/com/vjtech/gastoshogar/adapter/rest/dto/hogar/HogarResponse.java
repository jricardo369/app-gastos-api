package com.vjtech.gastoshogar.adapter.rest.dto.hogar;

import java.time.Instant;
import java.util.UUID;

public class HogarResponse {
    private UUID id;
    private String nombre;
    private String moneda;
    private String zonaHoraria;
    private Instant createdAt;
    private Instant updatedAt;

    public HogarResponse() {}

    public HogarResponse(UUID id, String nombre, String moneda, String zonaHoraria, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.nombre = nombre;
        this.moneda = moneda;
        this.zonaHoraria = zonaHoraria;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getMoneda() { return moneda; }
    public void setMoneda(String moneda) { this.moneda = moneda; }
    public String getZonaHoraria() { return zonaHoraria; }
    public void setZonaHoraria(String zonaHoraria) { this.zonaHoraria = zonaHoraria; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}