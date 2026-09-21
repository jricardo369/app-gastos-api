package com.vjtech.gastoshogar.adapter.rest.controller;

import com.vjtech.gastoshogar.adapter.rest.dto.deuda.DeudaRequest;
import com.vjtech.gastoshogar.adapter.rest.dto.deuda.DeudaResponse;
import com.vjtech.gastoshogar.adapter.rest.dto.deuda.PagoDeudaRequest;
import com.vjtech.gastoshogar.adapter.rest.dto.deuda.PagoDeudaResponse;
import com.vjtech.gastoshogar.adapter.rest.dto.common.ApiResponse;
import com.vjtech.gastoshogar.application.port.in.DeudaServicePort;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/deudas")
public class DeudaController {
    @Autowired
    private DeudaServicePort deudaServicePort;

    @GetMapping
    public ResponseEntity<ApiResponse<List<DeudaResponse>>> getAll(@RequestHeader("X-User-Id") UUID hogarId) {
        List<DeudaResponse> deudas = deudaServicePort.getAll(hogarId);
        return ResponseEntity.ok(ApiResponse.success(deudas));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<DeudaResponse>> create(@Valid @RequestBody DeudaRequest req, @RequestHeader("X-User-Id") UUID hogarId) {
        DeudaResponse response = deudaServicePort.create(req, hogarId);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DeudaResponse>> update(@PathVariable UUID id, @Valid @RequestBody DeudaRequest req) {
        DeudaResponse response = deudaServicePort.update(id, req);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
        deudaServicePort.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Deuda eliminada"));
    }

    @PostMapping("/{id}/pagos")
    public ResponseEntity<ApiResponse<PagoDeudaResponse>> addPago(@PathVariable UUID id, @Valid @RequestBody PagoDeudaRequest req) {
        PagoDeudaResponse response = deudaServicePort.addPago(id, req);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/resumen")
    public ResponseEntity<ApiResponse<DeudaResponse>> getSummary(@RequestHeader("X-User-Id") UUID hogarId) {
        DeudaResponse response = deudaServicePort.getSummary(hogarId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}