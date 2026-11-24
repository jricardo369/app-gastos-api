package com.vjtech.gastoshogar.adapter.rest.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class UsuarioUpdateRequest {
    @Size(min = 3, max = 50) private String user;
    @Size(min = 6, max = 100) private String password;
    @Size(max = 120) private String name;
    @Size(max = 200) private String address;
    @Size(max = 20) private String phone;
    @Email @Size(max = 120) private String email;

    public UsuarioUpdateRequest() {}

    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}