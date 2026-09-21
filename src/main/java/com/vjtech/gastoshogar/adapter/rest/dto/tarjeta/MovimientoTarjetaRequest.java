package com.vjtech.gastoshogar.adapter.rest.dto.tarjeta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class MovimientoTarjetaRequest {
    @NotBlank private String descripcion;
    @NotBlank private String fechaCompra;
    @NotBlank private String fechaCorte;
    @NotNull private BigDecimal importe;
    @NotBlank private String tipo;
    @NotBlank private String categoriaId;
    private Integer cuotas;
    private Integer cuotaActual;

    public MovimientoTarjetaRequest() {}

    public MovimientoTarjetaRequest(String descripcion, String fechaCompra, String fechaCorte, BigDecimal importe, String tipo, String categoriaId, Integer cuotas, Integer cuotaActual) {
        this.descripcion = descripcion;
        this.fechaCompra = fechaCompra;
        this.fechaCorte = fechaCorte;
        this.importe = importe;
        this.tipo = tipo;
        this.categoriaId = categoriaId;
        this.cuotas = cuotas;
        this.cuotaActual = cuotaActual;
    }

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
    public String getCategoriaId() { return categoriaId; }
    public void setCategoriaId(String categoriaId) { this.categoriaId = categoriaId; }
    public Integer getCuotas() { return cuotas; }
    public void setCuotas(Integer cuotas) { this.cuotas = cuotas; }
    public Integer getCuotaActual() { return cuotaActual; }
    public void setCuotaActual(Integer cuotaActual) { this.cuotaActual = cuotaActual; }
}