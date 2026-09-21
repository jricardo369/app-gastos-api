package com.vjtech.gastoshogar.application.service;

import com.vjtech.gastoshogar.adapter.persistence.entity.*;
import com.vjtech.gastoshogar.adapter.persistence.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PresupuestoServiceTest {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private HogarRepository hogarRepository;
    @Autowired
    private PresupuestoRepository presupuestoRepository;
    @Autowired
    private GastoRepository gastoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;

    private UUID hogarId;
    private UUID presupuestoId;

    private void initData() {
        gastoRepository.deleteAll();
        presupuestoRepository.deleteAll();
        hogarRepository.deleteAll();
        usuarioRepository.deleteAll();

        UUID userId = UUID.randomUUID();
        UsuarioEntity user = new UsuarioEntity();
        user.setId(userId);
        user.setEmail("test@test.com");
        user.setNombre("Test");
        user.setPasswordHash("hash");
        usuarioRepository.save(user);

        HogarEntity hogar = new HogarEntity();
        hogar.setId(UUID.randomUUID());
        hogar.setUserId(userId);
        hogar.setNombre("Mi Hogar");
        hogar.setMoneda("MXN");
        hogar.setZonaHoraria("America/Mexico_City");
        hogarRepository.save(hogar);
        hogarId = hogar.getId();

        PresupuestoEntity pres = new PresupuestoEntity();
        pres.setHogarId(hogarId);
        pres.setAnio(2026);
        pres.setMes(9);
        pres.setEfectivoQ1(new BigDecimal("13500"));
        pres.setValesQ1(new BigDecimal("1345"));
        pres.setNominaQ1(new BigDecimal("0"));
        pres.setEfectivoQ2(new BigDecimal("13500"));
        pres.setValesQ2(new BigDecimal("1345"));
        pres.setNominaQ2(new BigDecimal("0"));
        pres.setCreatedAt(Instant.now());
        pres.setUpdatedAt(Instant.now());
        PresupuestoEntity saved = presupuestoRepository.save(pres);
        presupuestoId = saved.getId();
    }

    @Test
    void testResumenTodoBien() {
        initData();
        PresupuestoEntity pres = presupuestoRepository.findById(presupuestoId).orElseThrow();
        BigDecimal presupuesto = pres.getEfectivoQ1().add(pres.getValesQ1()).add(pres.getNominaQ1())
                ;
        BigDecimal totalGastosPrevistos = new BigDecimal("14845");
        BigDecimal diferencia = presupuesto.subtract(totalGastosPrevistos);
        assertEquals(0, diferencia.compareTo(BigDecimal.ZERO));
    }

    @Test
    void testResumenSobraDinero() {
        initData();
        PresupuestoEntity pres = presupuestoRepository.findById(presupuestoId).orElseThrow();
        BigDecimal presupuesto = pres.getEfectivoQ1().add(pres.getValesQ1()).add(pres.getNominaQ1())
                ;
        BigDecimal gastosPrevistos = new BigDecimal("10000");
        BigDecimal diferencia = presupuesto.subtract(gastosPrevistos);
        assertTrue(diferencia.compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    void testResumenFaltaDinero() {
        initData();
        PresupuestoEntity pres = presupuestoRepository.findById(presupuestoId).orElseThrow();
        BigDecimal presupuesto = pres.getEfectivoQ1().add(pres.getValesQ1()).add(pres.getNominaQ1())
                ;
        BigDecimal gastosPrevistos = new BigDecimal("20000");
        BigDecimal diferencia = presupuesto.subtract(gastosPrevistos);
        assertTrue(diferencia.compareTo(BigDecimal.ZERO) < 0);
    }

    @Test
    void testBigDecimalPrecision() {
        BigDecimal a = new BigDecimal("13500.00");
        BigDecimal b = new BigDecimal("1345.00");
        BigDecimal sum = a.add(b).setScale(2, java.math.RoundingMode.HALF_UP);
        assertEquals(new BigDecimal("14845.00"), sum);
    }

    @Test
    void testGastoCountByQuincena() {
        initData();
        CategoriaEntity cat = new CategoriaEntity();
        cat.setHogarId(hogarId);
        cat.setNombre("Hogar");
        cat.setColor("#a8a4ff");
        cat.setEstado("ACTIVO");
        cat.setCreatedAt(Instant.now());
        cat.setUpdatedAt(Instant.now());
        CategoriaEntity savedCat = categoriaRepository.save(cat);

        GastoEntity gasto = new GastoEntity();
        gasto.setPresupuestoId(presupuestoId);
        gasto.setQuincena("Q1");
        gasto.setCategoriaId(savedCat.getId());
        gasto.setDescripcion("Test");
        gasto.setImporte(new BigDecimal("100"));
        gasto.setFecha("2026-09-01");
        gasto.setTipo("FIJO");
        gasto.setEstadoPago("PENDIENTE");
        gasto.setCreatedAt(Instant.now());
        gasto.setUpdatedAt(Instant.now());
        gastoRepository.save(gasto);

        List<GastoEntity> q1 = gastoRepository.findByPresupuestoIdAndQuincena(presupuestoId, "Q1");
        assertEquals(1, q1.size());
    }
}
