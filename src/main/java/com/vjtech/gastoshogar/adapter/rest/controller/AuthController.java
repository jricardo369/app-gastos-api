package com.vjtech.gastoshogar.adapter.rest.controller;

import com.vjtech.gastoshogar.adapter.rest.dto.auth.AuthResponse;
import com.vjtech.gastoshogar.adapter.rest.dto.auth.LoginRequest;
import com.vjtech.gastoshogar.adapter.rest.dto.auth.RegisterRequest;
import com.vjtech.gastoshogar.adapter.rest.dto.common.ApiResponse;
import com.vjtech.gastoshogar.application.port.in.AuthServicePort;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthServicePort authServicePort;

    public AuthController(AuthServicePort authServicePort) {
        this.authServicePort = authServicePort;
    }

    @SecurityRequirements
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(authServicePort.register(req)));
    }

    @SecurityRequirements
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest req) {
        return ResponseEntity.ok(ApiResponse.success(authServicePort.login(req)));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<AuthResponse>> getMe(@AuthenticationPrincipal UserDetails principal) {
        return ResponseEntity.ok(ApiResponse.success(authServicePort.getMe(principal.getUsername())));
    }
}