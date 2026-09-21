package com.vjtech.gastoshogar.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import java.util.List;

public class Tarjeta {
    private UUID id;
    private UUID hogarId;
    private String titular;
    private String numero;
    private String banco;
    private String estado;
    private Instant createdAt;
    private Instant updatedAt;
    private List<MovimientoTarjeta> movimientos;

    public Tarjeta() {}

    public Tarjeta(UUID id, UUID hogarId, String titular, String numero, String banco, String estado, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.hogarId = hogarId;
        this.titular = titular;
        this.numero = numero;
        this.banco = banco;
        this.estado = estado;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getHogarId() { return hogarId; }
    public void setHogarId(UUID hogarId) { this.hogarId = hogarId; }
    public String getTitular() { return titular; }
    public void setTitular(String titular) { this.titular = titular; }
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    public String getBanco() { return banco; }
    public void setBanco(String banco) { this.banco = banco; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
    public List<MovimientoTarjeta> getMovimientos() { return movimientos; }
    public void setMovimientos(List<MovimientoTarjeta> movimientos) { this.movimientos = movimientos; }
}