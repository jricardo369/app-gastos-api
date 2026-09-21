package com.vjtech.gastoshogar.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class Gasto {
    private UUID id;
    private UUID presupuestoId;
    private String quincena;
    private UUID categoriaId;
    private String descripcion;
    private BigDecimal importe;
    private String fecha;
    private String tipo;
    private String estadoPago;
    private String observaciones;
    private Instant createdAt;
    private Instant updatedAt;

    public Gasto() {}

    public Gasto(UUID id, UUID presupuestoId, String quincena, UUID categoriaId, String descripcion, BigDecimal importe, String fecha, String tipo, String estadoPago, String observaciones, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.presupuestoId = presupuestoId;
        this.quincena = quincena;
        this.categoriaId = categoriaId;
        this.descripcion = descripcion;
        this.importe = importe;
        this.fecha = fecha;
        this.tipo = tipo;
        this.estadoPago = estadoPago;
        this.observaciones = observaciones;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getPresupuestoId() { return presupuestoId; }
    public void setPresupuestoId(UUID presupuestoId) { this.presupuestoId = presupuestoId; }
    public String getQuincena() { return quincena; }
    public void setQuincena(String quincena) { this.quincena = quincena; }
    public UUID getCategoriaId() { return categoriaId; }
    public void setCategoriaId(UUID categoriaId) { this.categoriaId = categoriaId; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public BigDecimal getImporte() { return importe; }
    public void setImporte(BigDecimal importe) { this.importe = importe; }
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getEstadoPago() { return estadoPago; }
    public void setEstadoPago(String estadoPago) { this.estadoPago = estadoPago; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}