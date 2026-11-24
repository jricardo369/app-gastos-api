package com.vjtech.gastoshogar.adapter.rest.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginRequest {
    @NotBlank @Size(max = 50) private String user;
    @NotBlank private String password;

    public LoginRequest() {}

    public LoginRequest(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}