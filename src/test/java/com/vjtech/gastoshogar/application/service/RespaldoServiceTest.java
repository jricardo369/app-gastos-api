package com.vjtech.gastoshogar.application.service;

import com.vjtech.gastoshogar.adapter.persistence.repository.RespaldoRepository;
import com.vjtech.gastoshogar.adapter.persistence.repository.UsuarioRepository;
import com.vjtech.gastoshogar.adapter.rest.dto.auth.AuthResponse;
import com.vjtech.gastoshogar.adapter.rest.dto.auth.RegisterRequest;
import com.vjtech.gastoshogar.adapter.rest.dto.membresia.MembresiaRequest;
import com.vjtech.gastoshogar.adapter.rest.dto.respaldo.RespaldoRequest;
import com.vjtech.gastoshogar.adapter.rest.dto.respaldo.RespaldoResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class RespaldoServiceTest {

    @Autowired
    private RespaldoServiceImpl respaldoService;

    @Autowired
    private AuthServiceImpl authService;

    @Autowired
    private MembresiaServiceImpl membresiaService;

    @Autowired
    private RespaldoRepository respaldoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private String registrar(String user) {
        RegisterRequest req = new RegisterRequest();
        req.setUser(user);
        req.setEmail(user + "@test.com");
        req.setPassword("secreto123");
        req.setName("Nombre " + user);
        req.setMembresiaType("MONTH");
        AuthResponse res = authService.register(req);
        return res.getUsuario().getUser();
    }

    @Test
    void guardarCreaUnSoloRespaldoPorUsuario() {
        String user = registrar("resp1");

        RespaldoResponse res = respaldoService.guardar(user, new RespaldoRequest(1, "{\"gastos\":[]}"));

        assertThat(res.getId()).isNotNull();
        assertThat(res.getSchemaVersion()).isEqualTo(1);
        assertThat(res.getUpdatedAt()).isNotNull();
        assertThat(respaldoRepository.count()).isEqualTo(1);

        respaldoService.guardar(user, new RespaldoRequest(2, "{\"gastos\":[1]}"));

        assertThat(respaldoRepository.count()).isEqualTo(1);
        assertThat(respaldoService.obtener(user).getSchemaVersion()).isEqualTo(2);
    }

    @Test
    void obtenerDevuelveElPayloadCompleto() {
        String user = registrar("resp2");
        String payload = "{\"presupuesto\":{\"q1\":{}},\"gastos\":[],\"deudas\":[]}";
        respaldoService.guardar(user, new RespaldoRequest(1, payload));

        RespaldoResponse res = respaldoService.obtener(user);

        assertThat(res.getPayload()).isEqualTo(payload);
    }

    @Test
    void sinMembresiaActivaNoPermiteGuardarNiLeer() {
        String user = registrar("resp3");
        Long idUser = usuarioRepository.findByUser(user).orElseThrow().getIdUser();
        membresiaService.update(membresiaService.findActiva(idUser).orElseThrow().getId(),
                new MembresiaRequest(null, null, "INACTIVA"));

        assertThatThrownBy(() -> respaldoService.guardar(user, new RespaldoRequest(1, "{}")))
                .isInstanceOf(AccessDeniedException.class);
        assertThatThrownBy(() -> respaldoService.obtener(user))
                .isInstanceOf(AccessDeniedException.class);
    }
}
