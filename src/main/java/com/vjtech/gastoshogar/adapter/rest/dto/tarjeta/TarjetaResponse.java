package com.vjtech.gastoshogar.adapter.rest.dto.tarjeta;

import java.time.Instant;
import java.util.UUID;

public class TarjetaResponse {
    private UUID id;
    private String titular;
    private String numero;
    private String banco;
    private String estado;
    private Instant createdAt;
    private Instant updatedAt;

    public TarjetaResponse() {}

    public TarjetaResponse(UUID id, String titular, String numero, String banco, String estado, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.titular = titular;
        this.numero = numero;
        this.banco = banco;
        this.estado = estado;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
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
}