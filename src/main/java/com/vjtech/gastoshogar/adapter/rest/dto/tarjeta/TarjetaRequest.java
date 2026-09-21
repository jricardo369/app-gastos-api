package com.vjtech.gastoshogar.adapter.rest.dto.tarjeta;

import jakarta.validation.constraints.NotBlank;

public class TarjetaRequest {
    @NotBlank private String titular;
    @NotBlank private String numero;
    @NotBlank private String banco;

    public TarjetaRequest() {}

    public TarjetaRequest(String titular, String numero, String banco) {
        this.titular = titular;
        this.numero = numero;
        this.banco = banco;
    }

    public String getTitular() { return titular; }
    public void setTitular(String titular) { this.titular = titular; }
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    public String getBanco() { return banco; }
    public void setBanco(String banco) { this.banco = banco; }
}