package com.vjtech.gastoshogar.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class PagoDeuda {
    private UUID id;
    private UUID deudaId;
    private BigDecimal importe;
    private String fecha;
    private Instant createdAt;
    private Instant updatedAt;

    public PagoDeuda() {}

    public PagoDeuda(UUID id, UUID deudaId, BigDecimal importe, String fecha, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.deudaId = deudaId;
        this.importe = importe;
        this.fecha = fecha;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getDeudaId() { return deudaId; }
    public void setDeudaId(UUID deudaId) { this.deudaId = deudaId; }
    public BigDecimal getImporte() { return importe; }
    public void setImporte(BigDecimal importe) { this.importe = importe; }
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}