package com.vjtech.gastoshogar.application.service;

import com.vjtech.gastoshogar.adapter.config.JwtUtil;
import com.vjtech.gastoshogar.adapter.persistence.entity.UsuarioEntity;
import com.vjtech.gastoshogar.adapter.persistence.repository.UsuarioRepository;
import com.vjtech.gastoshogar.adapter.rest.dto.auth.AuthResponse;
import com.vjtech.gastoshogar.adapter.rest.dto.auth.LoginRequest;
import com.vjtech.gastoshogar.adapter.rest.dto.auth.RegisterRequest;
import com.vjtech.gastoshogar.adapter.rest.dto.membresia.MembresiaResponse;
import com.vjtech.gastoshogar.adapter.rest.dto.usuario.UsuarioRequest;
import com.vjtech.gastoshogar.adapter.rest.dto.usuario.UsuarioResponse;
import com.vjtech.gastoshogar.application.port.in.AuthServicePort;
import com.vjtech.gastoshogar.application.port.in.MembresiaServicePort;
import com.vjtech.gastoshogar.common.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthServicePort {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final MembresiaServicePort membresiaServicePort;
    private final UsuarioServiceImpl usuarioService;
    private final ModelMapper modelMapper;

    public AuthServiceImpl(UsuarioRepository usuarioRepository,
                           PasswordEncoder passwordEncoder,
                           JwtUtil jwtUtil,
                           AuthenticationManager authenticationManager,
                           MembresiaServicePort membresiaServicePort,
                           UsuarioServiceImpl usuarioService,
                           ModelMapper modelMapper) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.membresiaServicePort = membresiaServicePort;
        this.usuarioService = usuarioService;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest req) {
        UsuarioResponse usuario = usuarioService.create(toUsuarioRequest(req));
        MembresiaResponse membresia = membresiaServicePort.create(usuario.getIdUser(), req.getMembresiaType());
        UsuarioEntity entity = usuarioRepository.findById(usuario.getIdUser()).orElseThrow();
        return buildAuthResponse(jwtUtil.generateTokenForUser(entity), modelMapper.map(entity, UsuarioResponse.class), membresia);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest req) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUser(), req.getPassword())
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UsuarioEntity entity = usuarioRepository.findByUser(req.getUser())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + req.getUser()));

        UsuarioResponse usuario = modelMapper.map(entity, UsuarioResponse.class);
        Optional<MembresiaResponse> membresia = membresiaServicePort.findActiva(entity.getIdUser());
        return buildAuthResponse(jwtUtil.generateToken(userDetails), usuario, membresia.orElse(null));
    }

    @Override
    @Transactional(readOnly = true)
    public AuthResponse getMe(String username) {
        UsuarioEntity entity = usuarioRepository.findByUser(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + username));
        UsuarioResponse usuario = modelMapper.map(entity, UsuarioResponse.class);
        Optional<MembresiaResponse> membresia = membresiaServicePort.findActiva(entity.getIdUser());
        return buildAuthResponse(null, usuario, membresia.orElse(null));
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponse findUsuario(Long idUser) {
        return modelMapper.map(usuarioService.getEntity(idUser), UsuarioResponse.class);
    }

    private AuthResponse buildAuthResponse(String token, UsuarioResponse usuario, MembresiaResponse membresia) {
        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setTokenType("Bearer");
        response.setUsuario(usuario);
        response.setMembresia(membresia);
        response.setMembresiaActiva(membresia != null);
        return response;
    }

    private UsuarioRequest toUsuarioRequest(RegisterRequest req) {
        UsuarioRequest ur = new UsuarioRequest();
        ur.setUser(req.getUser());
        ur.setPassword(req.getPassword());
        ur.setName(req.getName());
        ur.setAddress(req.getAddress());
        ur.setPhone(req.getPhone());
        ur.setEmail(req.getEmail());
        return ur;
    }
}