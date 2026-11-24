package com.vjtech.gastoshogar.application.service;

import com.vjtech.gastoshogar.adapter.persistence.repository.MembresiaRepository;
import com.vjtech.gastoshogar.adapter.persistence.repository.UsuarioRepository;
import com.vjtech.gastoshogar.adapter.persistence.entity.UsuarioEntity;
import com.vjtech.gastoshogar.adapter.rest.dto.membresia.MembresiaRequest;
import com.vjtech.gastoshogar.adapter.rest.dto.membresia.MembresiaResponse;
import com.vjtech.gastoshogar.adapter.rest.dto.usuario.UsuarioResponse;
import com.vjtech.gastoshogar.common.exception.ResourceNotFoundException;
import com.vjtech.gastoshogar.common.exception.ValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class MembresiaServiceTest {

    @Autowired
    private MembresiaServiceImpl membresiaService;

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @Autowired
    private MembresiaRepository membresiaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Long usuario(String nombre) {
        UsuarioResponse u = usuarioService.create(crearUsuarioRequest(nombre));
        return u.getIdUser();
    }

    private com.vjtech.gastoshogar.adapter.rest.dto.usuario.UsuarioRequest crearUsuarioRequest(String nombre) {
        var req = new com.vjtech.gastoshogar.adapter.rest.dto.usuario.UsuarioRequest();
        req.setUser(nombre);
        req.setEmail(nombre + "@test.com");
        req.setPassword("secreto123");
        req.setName("Nombre " + nombre);
        return req;
    }

    @Test
    void createMensualFijaUnMesDeVigencia() {
        Long idUser = usuario("mensual");

        MembresiaResponse res = membresiaService.create(new MembresiaRequest(idUser, "MONTH", null));

        assertThat(res.getType()).isEqualTo("MONTH");
        assertThat(res.getEstado()).isEqualTo("ACTIVA");
        assertThat(res.getFechaInicio()).isEqualTo(LocalDate.now());
        assertThat(res.getFechaFin()).isEqualTo(LocalDate.now().plusMonths(1));
    }

    @Test
    void createAnualFijaDoceMesesDeVigencia() {
        Long idUser = usuario("anual");

        MembresiaResponse res = membresiaService.create(new MembresiaRequest(idUser, "YEAR", null));

        assertThat(res.getFechaFin()).isEqualTo(LocalDate.now().plusMonths(12));
    }

    @Test
    void createAceptaTipoEnMinusculas() {
        Long idUser = usuario("minus");

        MembresiaResponse res = membresiaService.create(new MembresiaRequest(idUser, "month", null));

        assertThat(res.getType()).isEqualTo("MONTH");
    }

    @Test
    void createConTipoInvalidoLanzaValidationException() {
        Long idUser = usuario("malo");

        assertThatThrownBy(() -> membresiaService.create(new MembresiaRequest(idUser, "TRIMESTRAL", null)))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("TRIMESTRAL");
    }

    @Test
    void createConEstadoInvalidoLanzaValidationException() {
        Long idUser = usuario("estado");

        assertThatThrownBy(() -> membresiaService.create(new MembresiaRequest(idUser, "MONTH", "PENDIENTE")))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("PENDIENTE");
    }

    @Test
    void createConUsuarioInexistenteLanzaNotFound() {
        assertThatThrownBy(() -> membresiaService.create(new MembresiaRequest(999L, "MONTH", null)))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void findActivaDevuelveLaMembresiaVigente() {
        Long idUser = usuario("vigente");
        membresiaService.create(idUser, "MONTH");

        assertThat(membresiaService.findActiva(idUser)).isPresent();
    }

    @Test
    void findActivaDevuelveVacioSiEstaVencida() {
        Long idUser = usuario("vencida");
        membresiaService.create(idUser, "MONTH");

        membresiaRepository.findByIdUserOrderByFechaInicioDesc(idUser).forEach(m -> {
            m.setFechaFin(LocalDate.now().minusDays(1));
            membresiaRepository.saveAndFlush(m);
        });

        assertThat(membresiaService.findActiva(idUser)).isEmpty();
    }

    @Test
    void findActivaDevuelveVacioSiEstaInactiva() {
        Long idUser = usuario("inactiva");
        membresiaService.create(new MembresiaRequest(idUser, "MONTH", "INACTIVA"));

        assertThat(membresiaService.findActiva(idUser)).isEmpty();
    }

    @Test
    void findActivaDevuelveVacioSiNoTieneMembresias() {
        Long idUser = usuario("sin");
        assertThat(membresiaService.findActiva(idUser)).isEmpty();
    }

    @Test
    void findActivaIgnoraMembresiasQueAunNoEmpiezan() {
        Long idUser = usuario("futura");
        MembresiaResponse creada = membresiaService.create(idUser, "MONTH");
        membresiaRepository.findById(creada.getId()).ifPresent(m -> {
            m.setFechaInicio(LocalDate.now().plusDays(5));
            m.setFechaFin(LocalDate.now().plusMonths(1).plusDays(5));
            membresiaRepository.saveAndFlush(m);
        });

        assertThat(membresiaService.findActiva(idUser)).isEmpty();
    }

    @Test
    void findByUsuarioDevuelveTodasOrdenadasPorInicioDesc() {
        Long idUser = usuario("varias");
        membresiaService.create(idUser, "MONTH");

        membresiaRepository.findByIdUserOrderByFechaInicioDesc(idUser).forEach(m -> {
            m.setFechaInicio(LocalDate.now().minusMonths(3));
            membresiaRepository.saveAndFlush(m);
        });
        membresiaService.create(idUser, "YEAR");

        List<MembresiaResponse> lista = membresiaService.findByUsuario(idUser);
        assertThat(lista).hasSize(2);
        assertThat(lista.get(0).getFechaInicio()).isAfter(lista.get(1).getFechaInicio());
    }

    @Test
    void updateRecalculaFechaFinAlCambiarDeTipo() {
        Long idUser = usuario("upd");
        MembresiaResponse creada = membresiaService.create(idUser, "MONTH");

        MembresiaResponse res = membresiaService.update(creada.getId(), new MembresiaRequest(idUser, "YEAR", "INACTIVA"));

        assertThat(res.getType()).isEqualTo("YEAR");
        assertThat(res.getEstado()).isEqualTo("INACTIVA");
        assertThat(res.getFechaFin()).isEqualTo(LocalDate.now().plusMonths(12));
    }

    @Test
    void updateSinTypeConservaElTipoActual() {
        Long idUser = usuario("keep");
        MembresiaResponse creada = membresiaService.create(idUser, "YEAR");

        MembresiaResponse res = membresiaService.update(creada.getId(), new MembresiaRequest(idUser, null, "ACTIVA"));

        assertThat(res.getType()).isEqualTo("YEAR");
        assertThat(res.getFechaFin()).isEqualTo(LocalDate.now().plusMonths(12));
    }

    @Test
    void deleteEliminaLaMembresia() {
        Long idUser = usuario("del");
        MembresiaResponse creada = membresiaService.create(idUser, "MONTH");

        membresiaService.delete(creada.getId());

        assertThatThrownBy(() -> membresiaService.findById(creada.getId()))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void crearMembresiaDirectamenteNoRequiereServicioDeUsuarios() {
        UsuarioEntity u = new UsuarioEntity();
        u.setUser("directo");
        u.setEmail("directo@test.com");
        u.setName("Directo");
        u.setPassword(passwordEncoder.encode("secreto123"));
        usuarioRepository.saveAndFlush(u);

        MembresiaResponse res = membresiaService.create(u.getIdUser(), "MONTH");

        assertThat(res.getIdUser()).isEqualTo(u.getIdUser());
    }
}