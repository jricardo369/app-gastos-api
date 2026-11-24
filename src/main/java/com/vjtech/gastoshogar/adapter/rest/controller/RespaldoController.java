package com.vjtech.gastoshogar.adapter.rest.controller;

import com.vjtech.gastoshogar.adapter.rest.dto.common.ApiResponse;
import com.vjtech.gastoshogar.adapter.rest.dto.respaldo.RespaldoRequest;
import com.vjtech.gastoshogar.adapter.rest.dto.respaldo.RespaldoResponse;
import com.vjtech.gastoshogar.application.port.in.RespaldoServicePort;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/respaldos")
@SecurityRequirement(name = "bearerAuth")
public class RespaldoController {
    private final RespaldoServicePort respaldoServicePort;

    public RespaldoController(RespaldoServicePort respaldoServicePort) {
        this.respaldoServicePort = respaldoServicePort;
    }

    @PostMapping
    public ApiResponse<RespaldoResponse> guardar(@AuthenticationPrincipal UserDetails principal,
                                                 @Valid @RequestBody RespaldoRequest req) {
        return ApiResponse.success(respaldoServicePort.guardar(principal.getUsername(), req));
    }

    @GetMapping
    public ApiResponse<RespaldoResponse> obtener(@AuthenticationPrincipal UserDetails principal) {
        return ApiResponse.success(respaldoServicePort.obtener(principal.getUsername()));
    }
}
