package com.vjtech.gastoshogar.adapter.rest.controller;

import com.vjtech.gastoshogar.adapter.rest.dto.presupuesto.IngresoUpdateRequest;
import com.vjtech.gastoshogar.adapter.rest.dto.presupuesto.PresupuestoRequest;
import com.vjtech.gastoshogar.adapter.rest.dto.presupuesto.PresupuestoResponse;
import com.vjtech.gastoshogar.adapter.rest.dto.presupuesto.ResumenResponse;
import com.vjtech.gastoshogar.adapter.rest.dto.common.ApiResponse;
import com.vjtech.gastoshogar.application.port.in.PresupuestoServicePort;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/presupuestos")
public class PresupuestoController {
    @Autowired
    private PresupuestoServicePort presupuestoServicePort;

    @PostMapping
    public ResponseEntity<ApiResponse<PresupuestoResponse>> create(@Valid @RequestBody PresupuestoRequest req, @RequestHeader("X-User-Id") UUID userId) {
        PresupuestoResponse response = presupuestoServicePort.create(req, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PresupuestoResponse>> getByHogarId(@RequestHeader("X-User-Id") UUID userId) {
        PresupuestoResponse response = presupuestoServicePort.getByHogarId(userId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/resumen")
    public ResponseEntity<ApiResponse<ResumenResponse>> getSummary(@RequestParam UUID hogarId, @RequestParam int anio, @RequestParam int mes) {
        ResumenResponse response = presupuestoServicePort.getSummary(hogarId, anio, mes);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/{presupuestoId}/ingresos")
    public ResponseEntity<ApiResponse<PresupuestoResponse>> updateIngresos(@PathVariable UUID presupuestoId, @RequestParam String quincena, @Valid @RequestBody List<IngresoUpdateRequest> ingresos) {
        PresupuestoResponse response = presupuestoServicePort.updateIngresos(presupuestoId, quincena, ingresos);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}