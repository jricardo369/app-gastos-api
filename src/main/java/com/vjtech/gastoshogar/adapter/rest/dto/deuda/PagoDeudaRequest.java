package com.vjtech.gastoshogar.adapter.rest.dto.deuda;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class PagoDeudaRequest {
    @NotNull private BigDecimal importe;
    @NotBlank private String fecha;

    public PagoDeudaRequest() {}

    public PagoDeudaRequest(BigDecimal importe, String fecha) {
        this.importe = importe;
        this.fecha = fecha;
    }

    public BigDecimal getImporte() { return importe; }
    public void setImporte(BigDecimal importe) { this.importe = importe; }
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
}