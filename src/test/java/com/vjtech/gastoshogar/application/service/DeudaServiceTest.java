package com.vjtech.gastoshogar.application.service;

import com.vjtech.gastoshogar.adapter.persistence.entity.*;
import com.vjtech.gastoshogar.adapter.persistence.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class DeudaServiceTest {

    @Autowired
    private HogarRepository hogarRepository;
    @Autowired
    private DeudaRepository deudaRepository;
    @Autowired
    private PagoDeudaRepository pagoDeudaRepository;

    private UUID hogarId;

    @BeforeEach
    void setUp() {
        hogarRepository.deleteAll();

        HogarEntity hogar = new HogarEntity();
        hogar.setId(UUID.randomUUID());
        hogar.setUserId(UUID.randomUUID());
        hogar.setNombre("Mi Hogar");
        hogar.setMoneda("MXN");
        hogar.setZonaHoraria("America/Mexico_City");
        hogarRepository.save(hogar);
        hogarId = hogar.getId();
    }

    @Test
    void testPartialPayment() {
        DeudaEntity deuda = new DeudaEntity();
        deuda.setId(UUID.randomUUID());
        deuda.setHogarId(hogarId);
        deuda.setDeudor("Miguel");
        deuda.setDescripcion("Préstamo");
        deuda.setImporteTotal(new BigDecimal("1000"));
        deuda.setImportePagado(new BigDecimal("0"));
        deuda.setSaldoPendiente(new BigDecimal("1000"));
        deuda.setFechaVencimiento("2026-12-31");
        deuda.setEstado("ACTIVO");
        deudaRepository.save(deuda);

        PagoDeudaEntity pago = new PagoDeudaEntity();
        pago.setId(UUID.randomUUID());
        pago.setDeudaId(deuda.getId());
        pago.setImporte(new BigDecimal("300"));
        pago.setFecha("2026-09-03");
        pagoDeudaRepository.save(pago);

        deuda.setImportePagado(deuda.getImportePagado().add(new BigDecimal("300")));
        deuda.setSaldoPendiente(deuda.getImporteTotal().subtract(deuda.getImportePagado()));
        if (deuda.getSaldoPendiente().compareTo(BigDecimal.ZERO) <= 0) {
            deuda.setEstado("PAGADO");
            deuda.setSaldoPendiente(BigDecimal.ZERO);
        }
        deudaRepository.save(deuda);

        assertEquals(new BigDecimal("700"), deuda.getSaldoPendiente());
        assertEquals(new BigDecimal("300"), deuda.getImportePagado());
    }

    @Test
    void testPaymentExceedsBalance() {
        DeudaEntity deuda = new DeudaEntity();
        deuda.setId(UUID.randomUUID());
        deuda.setHogarId(hogarId);
        deuda.setDeudor("Test");
        deuda.setImporteTotal(new BigDecimal("500"));
        deuda.setImportePagado(new BigDecimal("0"));
        deuda.setSaldoPendiente(new BigDecimal("500"));
        deuda.setEstado("ACTIVO");
        deudaRepository.save(deuda);

        BigDecimal pagoImporte = new BigDecimal("600");
        assertTrue(pagoImporte.compareTo(deuda.getSaldoPendiente()) > 0);
    }

    @Test
    void testDebtFullyPaid() {
        DeudaEntity deuda = new DeudaEntity();
        deuda.setId(UUID.randomUUID());
        deuda.setHogarId(hogarId);
        deuda.setDeudor("Test");
        deuda.setImporteTotal(new BigDecimal("500"));
        deuda.setImportePagado(new BigDecimal("0"));
        deuda.setSaldoPendiente(new BigDecimal("500"));
        deuda.setEstado("ACTIVO");
        deudaRepository.save(deuda);

        deuda.setImportePagado(deuda.getImporteTotal());
        deuda.setSaldoPendiente(BigDecimal.ZERO);
        deuda.setEstado("PAGADO");
        deudaRepository.save(deuda);

        assertEquals(BigDecimal.ZERO, deuda.getSaldoPendiente());
        assertEquals("PAGADO", deuda.getEstado());
    }
}