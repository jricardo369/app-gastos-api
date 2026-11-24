package com.vjtech.gastoshogar.adapter.rest.controller;

import com.vjtech.gastoshogar.adapter.rest.dto.common.ApiResponse;
import com.vjtech.gastoshogar.adapter.rest.dto.membresia.MembresiaRequest;
import com.vjtech.gastoshogar.adapter.rest.dto.membresia.MembresiaResponse;
import com.vjtech.gastoshogar.application.port.in.MembresiaServicePort;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/membresias")
public class MembresiaController {
    private final MembresiaServicePort membresiaServicePort;

    public MembresiaController(MembresiaServicePort membresiaServicePort) {
        this.membresiaServicePort = membresiaServicePort;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MembresiaResponse>> create(@Valid @RequestBody MembresiaRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(membresiaServicePort.create(req)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<MembresiaResponse>>> findByUsuario(@RequestParam Long idUser) {
        return ResponseEntity.ok(ApiResponse.success(membresiaServicePort.findByUsuario(idUser)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MembresiaResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(membresiaServicePort.findById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MembresiaResponse>> update(@PathVariable Long id,
                                                                @Valid @RequestBody MembresiaRequest req) {
        return ResponseEntity.ok(ApiResponse.success(membresiaServicePort.update(id, req)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        membresiaServicePort.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Membresía eliminada"));
    }
}