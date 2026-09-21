package com.vjtech.gastoshogar.adapter.rest.dto.deuda;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class PagoDeudaResponse {
    private UUID id;
    private BigDecimal importe;
    private String fecha;
    private Instant createdAt;

    public PagoDeudaResponse() {}

    public PagoDeudaResponse(UUID id, BigDecimal importe, String fecha, Instant createdAt) {
        this.id = id;
        this.importe = importe;
        this.fecha = fecha;
        this.createdAt = createdAt;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public BigDecimal getImporte() { return importe; }
    public void setImporte(BigDecimal importe) { this.importe = importe; }
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}