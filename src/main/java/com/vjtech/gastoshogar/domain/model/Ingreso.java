package com.vjtech.gastoshogar.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class Ingreso {
    private UUID id;
    private UUID presupuestoId;
    private String quincena;
    private String concepto;
    private BigDecimal monto;
    private Instant createdAt;
    private Instant updatedAt;

    public Ingreso() {}

    public Ingreso(UUID id, UUID presupuestoId, String quincena, String concepto, BigDecimal monto, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.presupuestoId = presupuestoId;
        this.quincena = quincena;
        this.concepto = concepto;
        this.monto = monto;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getPresupuestoId() { return presupuestoId; }
    public void setPresupuestoId(UUID presupuestoId) { this.presupuestoId = presupuestoId; }
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