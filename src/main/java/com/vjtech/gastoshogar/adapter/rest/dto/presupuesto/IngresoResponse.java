package com.vjtech.gastoshogar.adapter.rest.dto.presupuesto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class IngresoResponse {
    private UUID id;
    private String quincena;
    private String concepto;
    private BigDecimal monto;
    private Instant createdAt;
    private Instant updatedAt;

    public IngresoResponse() {}

    public IngresoResponse(UUID id, String quincena, String concepto, BigDecimal monto, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.quincena = quincena;
        this.concepto = concepto;
        this.monto = monto;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getQuincena() { return quincena; }
    public void setQuincena(String quincena) { this.quincena = quincena; }
    public String getConcepto() { return concepto; }
    public void setConcepto(String concepto) { this.concepto = concepto; }
    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}