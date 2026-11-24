package com.vjtech.gastoshogar.adapter.rest.dto.respaldo;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class RespaldoRequest {
    @Min(value = 1, message = "La versión del esquema debe ser al menos 1")
    private int schemaVersion;

    @NotBlank(message = "El payload es obligatorio")
    private String payload;

    public RespaldoRequest() {}

    public RespaldoRequest(int schemaVersion, String payload) {
        this.schemaVersion = schemaVersion;
        this.payload = payload;
    }

    public int getSchemaVersion() { return schemaVersion; }
    public void setSchemaVersion(int schemaVersion) { this.schemaVersion = schemaVersion; }
    public String getPayload() { return payload; }
    public void setPayload(String payload) { this.payload = payload; }
}
