package com.vjtech.gastoshogar.adapter.rest.dto.categoria;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoriaRequest {
    @NotBlank @Size(max = 100) private String nombre;
    @NotBlank @Size(max = 7) private String color;
    @NotBlank @Size(max = 50) private String icono;
    @NotBlank private String estado;

    public CategoriaRequest() {}

    public CategoriaRequest(String nombre, String color, String icono, String estado) {
        this.nombre = nombre;
        this.color = color;
        this.icono = icono;
        this.estado = estado;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public String getIcono() { return icono; }
    public void setIcono(String icono) { this.icono = icono; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}