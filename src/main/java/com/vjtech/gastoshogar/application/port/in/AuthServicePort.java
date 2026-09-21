package com.vjtech.gastoshogar.application.port.in;

import com.vjtech.gastoshogar.adapter.rest.dto.auth.LoginRequest;
import com.vjtech.gastoshogar.adapter.rest.dto.auth.RegisterRequest;
import com.vjtech.gastoshogar.adapter.rest.dto.auth.UserResponse;
import java.util.UUID;

public interface AuthServicePort {
    Object register(RegisterRequest req);
    Object login(LoginRequest req);
    UserResponse getMe(UUID userId);
}