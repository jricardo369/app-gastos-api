package com.vjtech.gastoshogar.adapter.rest.dto.common;

import java.time.Instant;
import java.util.Map;

public class ApiResponse<T> {
    private Instant timestamp;
    private int status;
    private String code;
    private String message;
    private T data;
    private Map<String, Object> metadata;

    public ApiResponse() {}

    public ApiResponse(Instant timestamp, int status, String code, String message, T data) {
        this.timestamp = timestamp;
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(Instant.now(), 200, "SUCCESS", "Operación exitosa", data);
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(Instant.now(), 200, "SUCCESS", message, data);
    }

    public static <T> ApiResponse<T> created(T data) {
        return new ApiResponse<>(Instant.now(), 201, "CREATED", "Recurso creado exitosamente", data);
    }

    public static <T> ApiResponse<T> error(int status, String code, String message) {
        return new ApiResponse<>(Instant.now(), status, code, message, null);
    }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
    public Map<String, Object> getMetadata() { return metadata; }
    public void setMetadata(Map<String, Object> metadata) { this.metadata = metadata; }
}