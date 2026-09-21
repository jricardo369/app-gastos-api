package com.vjtech.gastoshogar.application.service;

import com.vjtech.gastoshogar.adapter.persistence.entity.UsuarioEntity;
import com.vjtech.gastoshogar.adapter.persistence.repository.UsuarioRepository;
import com.vjtech.gastoshogar.application.port.in.AuthServicePort;
import com.vjtech.gastoshogar.adapter.rest.dto.auth.LoginRequest;
import com.vjtech.gastoshogar.adapter.rest.dto.auth.RegisterRequest;
import com.vjtech.gastoshogar.adapter.rest.dto.auth.AuthResponse;
import com.vjtech.gastoshogar.adapter.rest.dto.auth.UserResponse;
import com.vjtech.gastoshogar.adapter.config.JwtUtil;
import com.vjtech.gastoshogar.common.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthServicePort {
    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    public AuthServiceImpl(UsuarioRepository usuarioRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        this.usuarioRepository = usuarioRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Object register(RegisterRequest req) {
        if (usuarioRepository.findByEmail(req.getEmail()).isPresent()) {
            throw new ResourceNotFoundException("El email ya está registrado");
        }
        UsuarioEntity entity = new UsuarioEntity();
        entity.setEmail(req.getEmail());
        entity.setNombre(req.getNombre());
        entity.setPasswordHash(passwordEncoder.encode(req.getPassword()));
        UsuarioEntity saved = usuarioRepository.save(entity);
        UserResponse userResponse = modelMapper.map(saved, UserResponse.class);
        UserDetails userDetails = new User(entity.getEmail(), entity.getPasswordHash(), Collections.emptyList());
        String token = jwtUtil.generateToken(userDetails);
        return new AuthResponse(token, "Bearer", userResponse);
    }

    @Override
    public Object login(LoginRequest req) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UsuarioEntity usuario = usuarioRepository.findByEmail(req.getEmail())
            .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        UserResponse userResponse = modelMapper.map(usuario, UserResponse.class);
        String token = jwtUtil.generateToken(userDetails);
        return new AuthResponse(token, "Bearer", userResponse);
    }

    @Override
    public UserResponse getMe(UUID userId) {
        UsuarioEntity entity = usuarioRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        return modelMapper.map(entity, UserResponse.class);
    }
}