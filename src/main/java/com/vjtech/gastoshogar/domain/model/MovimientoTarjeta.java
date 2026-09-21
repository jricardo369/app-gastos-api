package com.vjtech.gastoshogar.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class MovimientoTarjeta {
    private UUID id;
    private UUID tarjetaId;
    private String descripcion;
    private String fechaCompra;
    private String fechaCorte;
    private BigDecimal importe;
    private String tipo;
    private UUID categoriaId;
    private Integer cuotas;
    private Integer cuotaActual;
    private String estado;
    private Instant createdAt;
    private Instant updatedAt;

    public MovimientoTarjeta() {}

    public MovimientoTarjeta(UUID id, UUID tarjetaId, String descripcion, String fechaCompra, String fechaCorte, BigDecimal importe, String tipo, UUID categoriaId, Integer cuotas, Integer cuotaActual, String estado, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.tarjetaId = tarjetaId;
        this.descripcion = descripcion;
        this.fechaCompra = fechaCompra;
        this.fechaCorte = fechaCorte;
        this.importe = importe;
        this.tipo = tipo;
        this.categoriaId = categoriaId;
        this.cuotas = cuotas;
        this.cuotaActual = cuotaActual;
        this.estado = estado;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getTarjetaId() { return tarjetaId; }
    public void setTarjetaId(UUID tarjetaId) { this.tarjetaId = tarjetaId; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getFechaCompra() { return fechaCompra; }
    public void setFechaCompra(String fechaCompra) { this.fechaCompra = fechaCompra; }
    public String getFechaCorte() { return fechaCorte; }
    public void setFechaCorte(String fechaCorte) { this.fechaCorte = fechaCorte; }
    public BigDecimal getImporte() { return importe; }
    public void setImporte(BigDecimal importe) { this.importe = importe; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public UUID getCategoriaId() { return categoriaId; }
    public void setCategoriaId(UUID categoriaId) { this.categoriaId = categoriaId; }
    public Integer getCuotas() { return cuotas; }
    public void setCuotas(Integer cuotas) { this.cuotas = cuotas; }
    public Integer getCuotaActual() { return cuotaActual; }
    public void setCuotaActual(Integer cuotaActual) { this.cuotaActual = cuotaActual; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}