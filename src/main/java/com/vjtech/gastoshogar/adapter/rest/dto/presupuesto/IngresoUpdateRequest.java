package com.vjtech.gastoshogar.adapter.rest.dto.presupuesto;

import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;

public class IngresoUpdateRequest {
    @NotBlank private String concepto;
    private BigDecimal monto;

    public IngresoUpdateRequest() {}

    public IngresoUpdateRequest(String concepto, BigDecimal monto) {
        this.concepto = concepto;
        this.monto = monto;
    }

    public String getConcepto() { return concepto; }
    public void setConcepto(String concepto) { this.concepto = concepto; }
    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }
}