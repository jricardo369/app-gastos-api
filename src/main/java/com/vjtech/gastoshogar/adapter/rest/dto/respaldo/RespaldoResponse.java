package com.vjtech.gastoshogar.adapter.rest.dto.respaldo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RespaldoResponse {
    private Long id;
    private int schemaVersion;
    private Instant updatedAt;
    private String payload;

    public RespaldoResponse() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public int getSchemaVersion() { return schemaVersion; }
    public void setSchemaVersion(int schemaVersion) { this.schemaVersion = schemaVersion; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
    public String getPayload() { return payload; }
    public void setPayload(String payload) { this.payload = payload; }
}
