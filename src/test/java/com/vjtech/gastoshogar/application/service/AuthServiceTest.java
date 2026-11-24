package com.vjtech.gastoshogar.application.service;

import com.vjtech.gastoshogar.adapter.persistence.entity.UsuarioEntity;
import com.vjtech.gastoshogar.adapter.persistence.repository.UsuarioRepository;
import com.vjtech.gastoshogar.adapter.rest.dto.auth.AuthResponse;
import com.vjtech.gastoshogar.adapter.rest.dto.auth.LoginRequest;
import com.vjtech.gastoshogar.adapter.rest.dto.auth.RegisterRequest;
import com.vjtech.gastoshogar.common.exception.ValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class AuthServiceTest {

    @Autowired
    private AuthServiceImpl authService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private RegisterRequest registerRequest(String user, String email, String type) {
        RegisterRequest req = new RegisterRequest();
        req.setUser(user);
        req.setEmail(email);
        req.setPassword("secreto123");
        req.setName("Nombre " + user);
        req.setAddress("Calle 1");
        req.setPhone("5551234567");
        req.setMembresiaType(type);
        return req;
    }

    @Test
    void registerCreaUsuarioYMembresiaMensualActiva() {
        AuthResponse res = authService.register(registerRequest("ana", "ana@test.com", "MONTH"));

        assertThat(res.getToken()).isNotBlank();
        assertThat(res.getUsuario().getUser()).isEqualTo("ana");
        assertThat(res.getUsuario().getName()).isEqualTo("Nombre ana");
        assertThat(res.getUsuario().getAddress()).isEqualTo("Calle 1");
        assertThat(res.getUsuario().getPhone()).isEqualTo("5551234567");
        assertThat(res.isMembresiaActiva()).isTrue();
        assertThat(res.getMembresia().getType()).isEqualTo("MONTH");
    }

    @Test
    void registerNoDevuelveElPassword() {
        AuthResponse res = authService.register(registerRequest("bob", "bob@test.com", "MONTH"));
        String serializado = res.getUsuario().toString();

        assertThat(serializado).doesNotContain("password").doesNotContain("secreto123");
    }

    @Test
    void registerGuardaPasswordConHash() {
        authService.register(registerRequest("carl", "carl@test.com", "YEAR"));

        UsuarioEntity guardado = usuarioRepository.findByUser("carl").orElseThrow();
        assertThat(guardado.getPassword()).isNotEqualTo("secreto123");
        assertThat(passwordEncoder.matches("secreto123", guardado.getPassword())).isTrue();
    }

    @Test
    void registerRechazaUsuarioDuplicado() {
        authService.register(registerRequest("dup", "dup@test.com", "MONTH"));

        assertThatThrownBy(() -> authService.register(registerRequest("dup", "otro@test.com", "MONTH")))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("dup");
    }

    @Test
    void registerRechazaMembresiaInvalida() {
        assertThatThrownBy(() -> authService.register(registerRequest("eve", "eve@test.com", "SEMANAL")))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("SEMANAL");
    }

    @Test
    void loginRegresaUsuarioSinPasswordYMembresiaActiva() {
        authService.register(registerRequest("leo", "leo@test.com", "YEAR"));

        AuthResponse res = authService.login(new LoginRequest("leo", "secreto123"));

        assertThat(res.getToken()).isNotBlank();
        assertThat(res.getTokenType()).isEqualTo("Bearer");
        assertThat(res.getUsuario().getEmail()).isEqualTo("leo@test.com");
        assertThat(res.isMembresiaActiva()).isTrue();
        assertThat(res.getMembresia().getType()).isEqualTo("YEAR");
        assertThat(res.getUsuario().toString()).doesNotContain("password");
    }

    @Test
    void loginSinMembresiaActivaReportaMembresiaActivaFalse() {
        UsuarioEntity u = new UsuarioEntity();
        u.setUser("sinmem");
        u.setEmail("sinmem@test.com");
        u.setName("Sin Membresia");
        u.setPassword(passwordEncoder.encode("secreto123"));
        usuarioRepository.saveAndFlush(u);

        AuthResponse res = authService.login(new LoginRequest("sinmem", "secreto123"));

        assertThat(res.isMembresiaActiva()).isFalse();
        assertThat(res.getMembresia()).isNull();
        assertThat(res.getUsuario().getUser()).isEqualTo("sinmem");
    }

    @Test
    void loginConPasswordIncorrectoFalla() {
        authService.register(registerRequest("mallory", "mallory@test.com", "MONTH"));

        assertThatThrownBy(() -> authService.login(new LoginRequest("mallory", "incorrecta")))
                .isInstanceOf(org.springframework.security.authentication.BadCredentialsException.class);
    }

    @Test
    void getMeRegresaUsuarioYMembresiaPorUsername() {
        AuthResponse registrado = authService.register(registerRequest("nina", "nina@test.com", "MONTH"));

        AuthResponse me = authService.getMe("nina");

        assertThat(me.getUsuario().getIdUser()).isEqualTo(registrado.getUsuario().getIdUser());
        assertThat(me.isMembresiaActiva()).isTrue();
        assertThat(me.getToken()).isNull();
    }
}