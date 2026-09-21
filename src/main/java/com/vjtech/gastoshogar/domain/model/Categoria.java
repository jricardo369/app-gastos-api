package com.vjtech.gastoshogar.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class Categoria {
    private UUID id;
    private UUID hogarId;
    private String nombre;
    private String color;
    private String icono;
    private String estado;
    private Instant createdAt;
    private Instant updatedAt;

    public Categoria() {}

    public Categoria(UUID id, UUID hogarId, String nombre, String color, String icono, String estado, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.hogarId = hogarId;
        this.nombre = nombre;
        this.color = color;
        this.icono = icono;
        this.estado = estado;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getHogarId() { return hogarId; }
    public void setHogarId(UUID hogarId) { this.hogarId = hogarId; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public String getIcono() { return icono; }
    public void setIcono(String icono) { this.icono = icono; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}