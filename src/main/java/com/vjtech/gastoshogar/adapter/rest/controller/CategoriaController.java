package com.vjtech.gastoshogar.adapter.rest.controller;

import com.vjtech.gastoshogar.adapter.rest.dto.categoria.CategoriaRequest;
import com.vjtech.gastoshogar.adapter.rest.dto.categoria.CategoriaResponse;
import com.vjtech.gastoshogar.adapter.rest.dto.common.ApiResponse;
import com.vjtech.gastoshogar.application.port.in.CategoriaServicePort;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/hogares/{hogarId}/categorias")
public class CategoriaController {
    @Autowired
    private CategoriaServicePort categoriaServicePort;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoriaResponse>>> getAll(@PathVariable UUID hogarId) {
        List<CategoriaResponse> categorias = categoriaServicePort.getAll(hogarId);
        return ResponseEntity.ok(ApiResponse.success(categorias));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CategoriaResponse>> create(@PathVariable UUID hogarId, @Valid @RequestBody CategoriaRequest req) {
        CategoriaResponse response = categoriaServicePort.create(req, hogarId);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoriaResponse>> update(@PathVariable UUID id, @PathVariable UUID hogarId, @Valid @RequestBody CategoriaRequest req) {
        CategoriaResponse response = categoriaServicePort.update(id, req, hogarId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id, @PathVariable UUID hogarId) {
        categoriaServicePort.delete(id, hogarId);
        return ResponseEntity.ok(ApiResponse.success(null, "Categoría eliminada"));
    }
}