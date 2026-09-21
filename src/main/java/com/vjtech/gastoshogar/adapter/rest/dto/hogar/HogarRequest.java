package com.vjtech.gastoshogar.adapter.rest.dto.hogar;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class HogarRequest {
    @NotBlank @Size(max = 100) private String nombre;
    @NotBlank @Size(max = 3) private String moneda;
    @NotBlank @Size(max = 100) private String zonaHoraria;

    public HogarRequest() {}

    public HogarRequest(String nombre, String moneda, String zonaHoraria) {
        this.nombre = nombre;
        this.moneda = moneda;
        this.zonaHoraria = zonaHoraria;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getMoneda() { return moneda; }
    public void setMoneda(String moneda) { this.moneda = moneda; }
    public String getZonaHoraria() { return zonaHoraria; }
    public void setZonaHoraria(String zonaHoraria) { this.zonaHoraria = zonaHoraria; }
}