package com.vjtech.gastoshogar.adapter.rest.dto.deuda;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class DeudaRequest {
    @NotBlank private String deudor;
    private String descripcion;
    @NotNull private BigDecimal importeTotal;
    @NotBlank private String fechaVencimiento;

    public DeudaRequest() {}

    public DeudaRequest(String deudor, String descripcion, BigDecimal importeTotal, String fechaVencimiento) {
        this.deudor = deudor;
        this.descripcion = descripcion;
        this.importeTotal = importeTotal;
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getDeudor() { return deudor; }
    public void setDeudor(String deudor) { this.deudor = deudor; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public BigDecimal getImporteTotal() { return importeTotal; }
    public void setImporteTotal(BigDecimal importeTotal) { this.importeTotal = importeTotal; }
    public String getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(String fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }
}