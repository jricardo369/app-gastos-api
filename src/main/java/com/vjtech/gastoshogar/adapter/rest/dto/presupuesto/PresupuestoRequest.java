package com.vjtech.gastoshogar.adapter.rest.dto.presupuesto;

import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;

public class PresupuestoRequest {
    private int anio;
    private int mes;
    private BigDecimal efectivoQ1;
    private BigDecimal valesQ1;
    private BigDecimal nominaQ1;
    private BigDecimal efectivoQ2;
    private BigDecimal valesQ2;
    private BigDecimal nominaQ2;

    public PresupuestoRequest() {}

    public PresupuestoRequest(int anio, int mes, BigDecimal efectivoQ1, BigDecimal valesQ1, BigDecimal nominaQ1, BigDecimal efectivoQ2, BigDecimal valesQ2, BigDecimal nominaQ2) {
        this.anio = anio;
        this.mes = mes;
        this.efectivoQ1 = efectivoQ1;
        this.valesQ1 = valesQ1;
        this.nominaQ1 = nominaQ1;
        this.efectivoQ2 = efectivoQ2;
        this.valesQ2 = valesQ2;
        this.nominaQ2 = nominaQ2;
    }

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
}