package com.vjtech.gastoshogar.adapter.rest.controller;

import com.vjtech.gastoshogar.adapter.rest.dto.tarjeta.MovimientoTarjetaResponse;
import com.vjtech.gastoshogar.adapter.rest.dto.tarjeta.TarjetaRequest;
import com.vjtech.gastoshogar.adapter.rest.dto.tarjeta.TarjetaResponse;
import com.vjtech.gastoshogar.adapter.rest.dto.common.ApiResponse;
import com.vjtech.gastoshogar.application.port.in.TarjetaServicePort;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tarjetas")
public class TarjetaController {
    @Autowired
    private TarjetaServicePort tarjetaServicePort;

    @GetMapping
    public ResponseEntity<ApiResponse<List<TarjetaResponse>>> getAll(@RequestHeader("X-User-Id") UUID hogarId) {
        List<TarjetaResponse> tarjetas = tarjetaServicePort.getAll(hogarId);
        return ResponseEntity.ok(ApiResponse.success(tarjetas));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TarjetaResponse>> create(@Valid @RequestBody TarjetaRequest req, @RequestHeader("X-User-Id") UUID hogarId) {
        TarjetaResponse response = tarjetaServicePort.create(req, hogarId);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TarjetaResponse>> getById(@PathVariable UUID id, @RequestHeader("X-User-Id") UUID hogarId) {
        TarjetaResponse response = tarjetaServicePort.getById(id, hogarId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TarjetaResponse>> update(@PathVariable UUID id, @Valid @RequestBody TarjetaRequest req) {
        TarjetaResponse response = tarjetaServicePort.update(id, req);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
        tarjetaServicePort.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Tarjeta eliminada"));
    }

    @GetMapping("/{id}/movimientos")
    public ResponseEntity<ApiResponse<List<MovimientoTarjetaResponse>>> getMovimientos(@PathVariable UUID id, @RequestParam(required = false) String estado, @RequestParam(required = false) String tipo) {
        List<MovimientoTarjetaResponse> movimientos = tarjetaServicePort.getMovimientos(id, estado, tipo);
        return ResponseEntity.ok(ApiResponse.success(movimientos));
    }
}