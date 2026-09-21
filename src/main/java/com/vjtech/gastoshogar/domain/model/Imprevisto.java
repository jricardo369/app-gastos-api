package com.vjtech.gastoshogar.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class Imprevisto {
    private UUID id;
    private UUID presupuestoId;
    private String quincena;
    private String descripcion;
    private BigDecimal importe;
    private String fecha;
    private String estadoPago;
    private Instant createdAt;
    private Instant updatedAt;

    public Imprevisto() {}

    public Imprevisto(UUID id, UUID presupuestoId, String quincena, String descripcion, BigDecimal importe, String fecha, String estadoPago, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.presupuestoId = presupuestoId;
        this.quincena = quincena;
        this.descripcion = descripcion;
        this.importe = importe;
        this.fecha = fecha;
        this.estadoPago = estadoPago;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getPresupuestoId() { return presupuestoId; }
    public void setPresupuestoId(UUID presupuestoId) { this.presupuestoId = presupuestoId; }
    public String getQuincena() { return quincena; }
    public void setQuincena(String quincena) { this.quincena = quincena; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public BigDecimal getImporte() { return importe; }
    public void setImporte(BigDecimal importe) { this.importe = importe; }
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public String getEstadoPago() { return estadoPago; }
    public void setEstadoPago(String estadoPago) { this.estadoPago = estadoPago; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}