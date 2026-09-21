package com.vjtech.gastoshogar.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import java.util.List;

public class Deuda {
    private UUID id;
    private UUID hogarId;
    private String deudor;
    private String descripcion;
    private BigDecimal importeTotal;
    private BigDecimal importePagado;
    private BigDecimal saldoPendiente;
    private String fechaVencimiento;
    private String estado;
    private Instant createdAt;
    private Instant updatedAt;
    private List<PagoDeuda> pagos;

    public Deuda() {}

    public Deuda(UUID id, UUID hogarId, String deudor, String descripcion, BigDecimal importeTotal, BigDecimal importePagado, BigDecimal saldoPendiente, String fechaVencimiento, String estado, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.hogarId = hogarId;
        this.deudor = deudor;
        this.descripcion = descripcion;
        this.importeTotal = importeTotal;
        this.importePagado = importePagado;
        this.saldoPendiente = saldoPendiente;
        this.fechaVencimiento = fechaVencimiento;
        this.estado = estado;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getHogarId() { return hogarId; }
    public void setHogarId(UUID hogarId) { this.hogarId = hogarId; }
    public String getDeudor() { return deudor; }
    public void setDeudor(String deudor) { this.deudor = deudor; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public BigDecimal getImporteTotal() { return importeTotal; }
    public void setImporteTotal(BigDecimal importeTotal) { this.importeTotal = importeTotal; }
    public BigDecimal getImportePagado() { return importePagado; }
    public void setImportePagado(BigDecimal importePagado) { this.importePagado = importePagado; }
    public BigDecimal getSaldoPendiente() { return saldoPendiente; }
    public void setSaldoPendiente(BigDecimal saldoPendiente) { this.saldoPendiente = saldoPendiente; }
    public String getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(String fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
    public List<PagoDeuda> getPagos() { return pagos; }
    public void setPagos(List<PagoDeuda> pagos) { this.pagos = pagos; }
}