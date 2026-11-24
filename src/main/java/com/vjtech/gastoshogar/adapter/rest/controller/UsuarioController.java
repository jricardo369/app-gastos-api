package com.vjtech.gastoshogar.adapter.rest.controller;

import com.vjtech.gastoshogar.adapter.rest.dto.common.ApiResponse;
import com.vjtech.gastoshogar.adapter.rest.dto.usuario.UsuarioRequest;
import com.vjtech.gastoshogar.adapter.rest.dto.usuario.UsuarioResponse;
import com.vjtech.gastoshogar.adapter.rest.dto.usuario.UsuarioUpdateRequest;
import com.vjtech.gastoshogar.application.port.in.UsuarioServicePort;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {
    private final UsuarioServicePort usuarioServicePort;

    public UsuarioController(UsuarioServicePort usuarioServicePort) {
        this.usuarioServicePort = usuarioServicePort;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UsuarioResponse>> create(@Valid @RequestBody UsuarioRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(usuarioServicePort.create(req)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UsuarioResponse>>> findAll() {
        return ResponseEntity.ok(ApiResponse.success(usuarioServicePort.findAll()));
    }

    @GetMapping("/{idUser}")
    public ResponseEntity<ApiResponse<UsuarioResponse>> findById(@PathVariable Long idUser) {
        return ResponseEntity.ok(ApiResponse.success(usuarioServicePort.findById(idUser)));
    }

    @PutMapping("/{idUser}")
    public ResponseEntity<ApiResponse<UsuarioResponse>> update(@PathVariable Long idUser,
                                                             @Valid @RequestBody UsuarioUpdateRequest req) {
        return ResponseEntity.ok(ApiResponse.success(usuarioServicePort.update(idUser, req)));
    }

    @DeleteMapping("/{idUser}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long idUser) {
        usuarioServicePort.delete(idUser);
        return ResponseEntity.ok(ApiResponse.success(null, "Usuario eliminado"));
    }
}