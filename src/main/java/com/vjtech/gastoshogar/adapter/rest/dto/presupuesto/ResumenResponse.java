package com.vjtech.gastoshogar.adapter.rest.dto.presupuesto;

import java.math.BigDecimal;
import java.util.List;

public class ResumenResponse {
    private BigDecimal presupuesto;
    private BigDecimal totalGastosPrevistos;
    private BigDecimal totalGastosReales;
    private BigDecimal saldoDisponible;
    private BigDecimal diferencia;
    private String estado;
    private BigDecimal importeDiferencia;
    private List<IngresoResponse> ingresosQ1;
    private List<IngresoResponse> ingresosQ2;

    public ResumenResponse() {}

    public ResumenResponse(BigDecimal presupuesto, BigDecimal totalGastosPrevistos, BigDecimal totalGastosReales, BigDecimal saldoDisponible, BigDecimal diferencia, String estado, BigDecimal importeDiferencia, List<IngresoResponse> ingresosQ1, List<IngresoResponse> ingresosQ2) {
        this.presupuesto = presupuesto;
        this.totalGastosPrevistos = totalGastosPrevistos;
        this.totalGastosReales = totalGastosReales;
        this.saldoDisponible = saldoDisponible;
        this.diferencia = diferencia;
        this.estado = estado;
        this.importeDiferencia = importeDiferencia;
        this.ingresosQ1 = ingresosQ1;
        this.ingresosQ2 = ingresosQ2;
    }

    public BigDecimal getPresupuesto() { return presupuesto; }
    public void setPresupuesto(BigDecimal presupuesto) { this.presupuesto = presupuesto; }
    public BigDecimal getTotalGastosPrevistos() { return totalGastosPrevistos; }
    public void setTotalGastosPrevistos(BigDecimal totalGastosPrevistos) { this.totalGastosPrevistos = totalGastosPrevistos; }
    public BigDecimal getTotalGastosReales() { return totalGastosReales; }
    public void setTotalGastosReales(BigDecimal totalGastosReales) { this.totalGastosReales = totalGastosReales; }
    public BigDecimal getSaldoDisponible() { return saldoDisponible; }
    public void setSaldoDisponible(BigDecimal saldoDisponible) { this.saldoDisponible = saldoDisponible; }
    public BigDecimal getDiferencia() { return diferencia; }
    public void setDiferencia(BigDecimal diferencia) { this.diferencia = diferencia; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public BigDecimal getImporteDiferencia() { return importeDiferencia; }
    public void setImporteDiferencia(BigDecimal importeDiferencia) { this.importeDiferencia = importeDiferencia; }
    public List<IngresoResponse> getIngresosQ1() { return ingresosQ1; }
    public void setIngresosQ1(List<IngresoResponse> ingresosQ1) { this.ingresosQ1 = ingresosQ1; }
    public List<IngresoResponse> getIngresosQ2() { return ingresosQ2; }
    public void setIngresosQ2(List<IngresoResponse> ingresosQ2) { this.ingresosQ2 = ingresosQ2; }
}