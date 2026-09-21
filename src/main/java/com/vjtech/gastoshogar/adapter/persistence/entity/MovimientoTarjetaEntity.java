package com.vjtech.gastoshogar.adapter.persistence.entity;

import jakarta.persistence.*;
import java.util.UUID;
import java.math.BigDecimal;
import java.time.Instant;

@Entity(name = "movimientos_tarjeta")
public class MovimientoTarjetaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "tarjeta_id", nullable = false)
    private UUID tarjetaId;

    @Column(nullable = false)
    private String descripcion;

    @Column(name = "fecha_compra", nullable = false)
    private String fechaCompra;

    @Column(name = "fecha_corte", nullable = false)
    private String fechaCorte;

    @Column(nullable = false)
    private BigDecimal importe;

    @Column(nullable = false)
    private String tipo;

    @Column(name = "categoria_id", nullable = false)
    private UUID categoriaId;

    @Column
    private Integer cuotas;

    @Column(name = "cuota_actual")
    private Integer cuotaActual;

    @Column(nullable = false)
    private String estado;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
        updatedAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
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