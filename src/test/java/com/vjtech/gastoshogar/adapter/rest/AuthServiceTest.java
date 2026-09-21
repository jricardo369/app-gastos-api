package com.vjtech.gastoshogar.adapter.rest;

import com.vjtech.gastoshogar.adapter.persistence.entity.UsuarioEntity;
import com.vjtech.gastoshogar.adapter.persistence.repository.UsuarioRepository;
import com.vjtech.gastoshogar.adapter.config.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AuthServiceTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        usuarioRepository.deleteAll();
    }

    @Test
    void testRegisterAndFindByEmail() {
        UsuarioEntity user = new UsuarioEntity();
        user.setId(UUID.randomUUID());
        user.setEmail("test@example.com");
        user.setNombre("Test User");
        user.setPasswordHash(passwordEncoder.encode("password123"));
        usuarioRepository.save(user);

        Optional<UsuarioEntity> found = usuarioRepository.findByEmail("test@example.com");
        assertTrue(found.isPresent());
        assertEquals("test@example.com", found.get().getEmail());
        assertTrue(passwordEncoder.matches("password123", found.get().getPasswordHash()));
    }

    @Test
    void testPasswordNotReturned() {
        UsuarioEntity user = new UsuarioEntity();
        user.setId(UUID.randomUUID());
        user.setEmail("secret@example.com");
        user.setNombre("Secret");
        user.setPasswordHash(passwordEncoder.encode("secret123"));
        usuarioRepository.save(user);

        Optional<UsuarioEntity> found = usuarioRepository.findByEmail("secret@example.com");
        assertTrue(found.isPresent());
        assertNotEquals("secret123", found.get().getPasswordHash());
    }

    @Test
    void testUserIsolation() {
        UsuarioEntity user1 = new UsuarioEntity();
        user1.setId(UUID.randomUUID());
        user1.setEmail("user1@example.com");
        user1.setNombre("User1");
        user1.setPasswordHash(passwordEncoder.encode("pass1"));
        usuarioRepository.save(user1);

        UsuarioEntity user2 = new UsuarioEntity();
        user2.setId(UUID.randomUUID());
        user2.setEmail("user2@example.com");
        user2.setNombre("User2");
        user2.setPasswordHash(passwordEncoder.encode("pass2"));
        usuarioRepository.save(user2);

        Optional<UsuarioEntity> found = usuarioRepository.findByEmail("user1@example.com");
        assertTrue(found.isPresent());
        assertEquals("user1@example.com", found.get().getEmail());

        Optional<UsuarioEntity> notFound = usuarioRepository.findByEmail("nonexistent@example.com");
        assertFalse(notFound.isPresent());
    }

    @Test
    void testJwtTokenGeneration() {
        var userDetails = new org.springframework.security.core.userdetails.User("test@example.com", "password", java.util.List.of());
        String token = jwtUtil.generateToken(userDetails);
        assertNotNull(token);
        String username = jwtUtil.extractUsername(token);
        assertEquals("test@example.com", username);
        assertTrue(jwtUtil.validateToken(token, userDetails));
    }
}