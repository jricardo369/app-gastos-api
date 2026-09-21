package com.vjtech.gastoshogar.adapter.rest.dto.gasto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class GastoRequest {
    @NotBlank private String descripcion;
    @NotNull private BigDecimal importe;
    @NotBlank private String categoriaId;
    @NotBlank private String quincena;
    @NotBlank private String fecha;
    @NotBlank private String tipo;
    @NotBlank private String estadoPago;
    private String observaciones;

    public GastoRequest() {}

    public GastoRequest(String descripcion, BigDecimal importe, String categoriaId, String quincena, String fecha, String tipo, String estadoPago, String observaciones) {
        this.descripcion = descripcion;
        this.importe = importe;
        this.categoriaId = categoriaId;
        this.quincena = quincena;
        this.fecha = fecha;
        this.tipo = tipo;
        this.estadoPago = estadoPago;
        this.observaciones = observaciones;
    }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public BigDecimal getImporte() { return importe; }
    public void setImporte(BigDecimal importe) { this.importe = importe; }
    public String getCategoriaId() { return categoriaId; }
    public void setCategoriaId(String categoriaId) { this.categoriaId = categoriaId; }
    public String getQuincena() { return quincena; }
    public void setQuincena(String quincena) { this.quincena = quincena; }
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getEstadoPago() { return estadoPago; }
    public void setEstadoPago(String estadoPago) { this.estadoPago = estadoPago; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}