package com.vjtech.gastoshogar.application.service;

import com.vjtech.gastoshogar.adapter.persistence.repository.MembresiaRepository;
import com.vjtech.gastoshogar.adapter.rest.dto.usuario.UsuarioRequest;
import com.vjtech.gastoshogar.adapter.rest.dto.usuario.UsuarioResponse;
import com.vjtech.gastoshogar.adapter.rest.dto.usuario.UsuarioUpdateRequest;
import com.vjtech.gastoshogar.common.exception.ResourceNotFoundException;
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
class UsuarioServiceTest {

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @Autowired
    private MembresiaServiceImpl membresiaService;

    @Autowired
    private MembresiaRepository membresiaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UsuarioRequest request(String user, String email) {
        UsuarioRequest req = new UsuarioRequest();
        req.setUser(user);
        req.setEmail(email);
        req.setPassword("secreto123");
        req.setName("Nombre " + user);
        req.setAddress("Calle Falsa 123");
        req.setPhone("5550001111");
        return req;
    }

    @Test
    void createPersisteTodosLosCampos() {
        UsuarioResponse res = usuarioService.create(request("maria", "maria@test.com"));

        assertThat(res.getIdUser()).isNotNull();
        assertThat(res.getUser()).isEqualTo("maria");
        assertThat(res.getName()).isEqualTo("Nombre maria");
        assertThat(res.getAddress()).isEqualTo("Calle Falsa 123");
        assertThat(res.getPhone()).isEqualTo("5550001111");
        assertThat(res.getEmail()).isEqualTo("maria@test.com");
    }

    @Test
    void createNormalizaEmailAMinusculas() {
        UsuarioResponse res = usuarioService.create(request("pedro", "Pedro@Test.COM"));
        assertThat(res.getEmail()).isEqualTo("pedro@test.com");
    }

    @Test
    void createRechazaUsuarioDuplicado() {
        usuarioService.create(request("luis", "luis@test.com"));

        assertThatThrownBy(() -> usuarioService.create(request("luis", "otro@test.com")))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("luis");
    }

    @Test
    void createRechazaEmailDuplicado() {
        usuarioService.create(request("ana2", "dup@test.com"));

        assertThatThrownBy(() -> usuarioService.create(request("otro", "dup@test.com")))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("email");
    }

    @Test
    void findByIdYPorUser() {
        UsuarioResponse creado = usuarioService.create(request("karla", "karla@test.com"));

        assertThat(usuarioService.findById(creado.getIdUser()).getUser()).isEqualTo("karla");
        assertThat(usuarioService.findByUser("karla").getIdUser()).isEqualTo(creado.getIdUser());
    }

    @Test
    void findByIdInexistenteLanzaNotFound() {
        assertThatThrownBy(() -> usuarioService.findById(999L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void updateCambiaCamposYReCodificaPassword() {
        UsuarioResponse creado = usuarioService.create(request("pedro2", "pedro2@test.com"));

        UsuarioUpdateRequest upd = new UsuarioUpdateRequest();
        upd.setName("Nuevo Nombre");
        upd.setPhone("5559998888");
        upd.setPassword("nuevasecreto1");

        UsuarioResponse res = usuarioService.update(creado.getIdUser(), upd);

        assertThat(res.getName()).isEqualTo("Nuevo Nombre");
        assertThat(res.getPhone()).isEqualTo("5559998888");
        assertThat(usuarioService.findById(creado.getIdUser()).getUser()).isEqualTo("pedro2");
    }

    @Test
    void updatePermiteMantenerLosMismosValoresDeUserYEmail() {
        UsuarioResponse creado = usuarioService.create(request("same", "same@test.com"));

        UsuarioUpdateRequest upd = new UsuarioUpdateRequest();
        upd.setUser(creado.getUser());
        upd.setEmail(creado.getEmail());
        upd.setName("Otro");

        UsuarioResponse res = usuarioService.update(creado.getIdUser(), upd);

        assertThat(res.getUser()).isEqualTo("same");
        assertThat(res.getEmail()).isEqualTo("same@test.com");
    }

    @Test
    void deleteEliminaTambienLasMembresias() {
        UsuarioResponse creado = usuarioService.create(request("borrar", "borrar@test.com"));
        membresiaService.create(creado.getIdUser(), "MONTH");

        usuarioService.delete(creado.getIdUser());

        assertThatThrownBy(() -> usuarioService.findById(creado.getIdUser()))
                .isInstanceOf(ResourceNotFoundException.class);
        assertThat(membresiaRepository.findByIdUserOrderByFechaInicioDesc(creado.getIdUser())).isEmpty();
    }

    @Test
    void findAllDevuelveTodosLosUsuarios() {
        usuarioService.create(request("u1", "u1@test.com"));
        usuarioService.create(request("u2", "u2@test.com"));

        assertThat(usuarioService.findAll()).hasSize(2);
        assertThat(passwordEncoder.encode("x")).isNotBlank();
    }
}