package com.vjtech.gastoshogar.adapter.persistence.entity;

import jakarta.persistence.*;
import java.util.UUID;
import java.math.BigDecimal;
import java.time.Instant;

@Entity(name = "presupuestos")
public class PresupuestoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "hogar_id", nullable = false)
    private UUID hogarId;

    @Column(nullable = false)
    private int anio;

    @Column(nullable = false)
    private int mes;

    @Column(name = "efectivo_q1", nullable = false)
    private BigDecimal efectivoQ1;

    @Column(name = "vales_q1", nullable = false)
    private BigDecimal valesQ1;

    @Column(name = "nomina_q1", nullable = false)
    private BigDecimal nominaQ1;

    @Column(name = "efectivo_q2", nullable = false)
    private BigDecimal efectivoQ2;

    @Column(name = "vales_q2", nullable = false)
    private BigDecimal valesQ2;

    @Column(name = "nomina_q2", nullable = false)
    private BigDecimal nominaQ2;

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
    public UUID getHogarId() { return hogarId; }
    public void setHogarId(UUID hogarId) { this.hogarId = hogarId; }
    public int getAnio() { return anio; }
    public void setAnio(int anio) { this.anio = anio; }
    public int getMes() { return mes; }
    public void setMes(int mes) { this.mes = mes; }
    public BigDecimal getEfectivoQ1() { return efectivoQ1; }
    public void setEfectivoQ1(BigDecimal efectivoQ1) { this.efectivoQ1 = efectivoQ1; }
    public BigDecimal getValesQ1() { return valesQ1; }
    public void setValesQ1(BigDecimal valesQ1) { this.valesQ1 = valesQ1; }
    public BigDecimal getNominaQ1() { return nominaQ1; }
    public void setNominaQ1(BigDecimal nominaQ1) { this.nominaQ1 = nominaQ1; }
    public BigDecimal getEfectivoQ2() { return efectivoQ2; }
    public void setEfectivoQ2(BigDecimal efectivoQ2) { this.efectivoQ2 = efectivoQ2; }
    public BigDecimal getValesQ2() { return valesQ2; }
    public void setValesQ2(BigDecimal valesQ2) { this.valesQ2 = valesQ2; }
    public BigDecimal getNominaQ2() { return nominaQ2; }
    public void setNominaQ2(BigDecimal nominaQ2) { this.nominaQ2 = nominaQ2; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}