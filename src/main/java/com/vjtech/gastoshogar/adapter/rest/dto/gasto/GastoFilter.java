package com.vjtech.gastoshogar.adapter.rest.dto.gasto;

import java.math.BigDecimal;
import java.util.Date;

public class GastoFilter {
    private String quincena;
    private String categoriaId;
    private String tipo;
    private String estadoPago;
    private Date fechaInicio;
    private Date fechaFin;

    public GastoFilter() {}

    public GastoFilter(String quincena, String categoriaId, String tipo, String estadoPago, Date fechaInicio, Date fechaFin) {
        this.quincena = quincena;
        this.categoriaId = categoriaId;
        this.tipo = tipo;
        this.estadoPago = estadoPago;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public String getQuincena() { return quincena; }
    public void setQuincena(String quincena) { this.quincena = quincena; }
    public String getCategoriaId() { return categoriaId; }
    public void setCategoriaId(String categoriaId) { this.categoriaId = categoriaId; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getEstadoPago() { return estadoPago; }
    public void setEstadoPago(String estadoPago) { this.estadoPago = estadoPago; }
    public Date getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }
    public Date getFechaFin() { return fechaFin; }
    public void setFechaFin(Date fechaFin) { this.fechaFin = fechaFin; }
}