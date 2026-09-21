package com.vjtech.gastoshogar.adapter.rest.controller;

import com.vjtech.gastoshogar.adapter.rest.dto.gasto.GastoFilter;
import com.vjtech.gastoshogar.adapter.rest.dto.gasto.GastoRequest;
import com.vjtech.gastoshogar.adapter.rest.dto.gasto.GastoResponse;
import com.vjtech.gastoshogar.adapter.rest.dto.common.ApiResponse;
import com.vjtech.gastoshogar.application.port.in.GastoServicePort;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/gastos")
public class GastoController {
    @Autowired
    private GastoServicePort gastoServicePort;

    @GetMapping
    public ResponseEntity<ApiResponse<List<GastoResponse>>> getAll(@RequestParam UUID presupuestoId, @ModelAttribute GastoFilter filter) {
        List<GastoResponse> gastos = gastoServicePort.getAll(presupuestoId, filter);
        return ResponseEntity.ok(ApiResponse.success(gastos));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<GastoResponse>> create(@RequestParam UUID presupuestoId, @Valid @RequestBody GastoRequest req) {
        GastoResponse response = gastoServicePort.create(presupuestoId, req);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<GastoResponse>> update(@PathVariable UUID id, @Valid @RequestBody GastoRequest req) {
        GastoResponse response = gastoServicePort.update(id, req);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
        gastoServicePort.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Gasto eliminado"));
    }

    @PostMapping("/{id}/pagar")
    public ResponseEntity<ApiResponse<GastoResponse>> togglePaid(@PathVariable UUID id) {
        GastoResponse response = gastoServicePort.togglePaid(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/{id}/marcar-pagado")
    public ResponseEntity<ApiResponse<GastoResponse>> markAsPaid(@PathVariable UUID id) {
        GastoResponse response = gastoServicePort.markAsPaid(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}