package com.vjtech.gastoshogar.adapter.rest.controller;

import com.vjtech.gastoshogar.adapter.rest.dto.hogar.HogarRequest;
import com.vjtech.gastoshogar.adapter.rest.dto.hogar.HogarResponse;
import com.vjtech.gastoshogar.adapter.rest.dto.common.ApiResponse;
import com.vjtech.gastoshogar.application.port.in.HogarServicePort;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/hogares")
public class HogarController {
    @Autowired
    private HogarServicePort hogarServicePort;

    @GetMapping
    public ResponseEntity<ApiResponse<List<HogarResponse>>> getAll(@RequestHeader("X-User-Id") UUID userId) {
        List<HogarResponse> hogares = hogarServicePort.getAll(userId);
        return ResponseEntity.ok(ApiResponse.success(hogares));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<HogarResponse>> getById(@PathVariable UUID id, @RequestHeader("X-User-Id") UUID userId) {
        HogarResponse response = hogarServicePort.getById(id, userId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<HogarResponse>> create(@Valid @RequestBody HogarRequest req, @RequestHeader("X-User-Id") UUID userId) {
        HogarResponse response = hogarServicePort.create(req, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<HogarResponse>> update(@PathVariable UUID id, @Valid @RequestBody HogarRequest req, @RequestHeader("X-User-Id") UUID userId) {
        HogarResponse response = hogarServicePort.update(id, req, userId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id, @RequestHeader("X-User-Id") UUID userId) {
        hogarServicePort.delete(id, userId);
        return ResponseEntity.ok(ApiResponse.success(null, "Hogar eliminado"));
    }
}