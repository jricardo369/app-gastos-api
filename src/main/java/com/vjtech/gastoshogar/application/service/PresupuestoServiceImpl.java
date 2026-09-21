package com.vjtech.gastoshogar.application.service;

import com.vjtech.gastoshogar.adapter.persistence.entity.GastoEntity;
import com.vjtech.gastoshogar.adapter.persistence.entity.ImprevistoEntity;
import com.vjtech.gastoshogar.adapter.persistence.entity.IngresoEntity;
import com.vjtech.gastoshogar.adapter.persistence.entity.PresupuestoEntity;
import com.vjtech.gastoshogar.adapter.persistence.repository.GastoRepository;
import com.vjtech.gastoshogar.adapter.persistence.repository.ImprevistoRepository;
import com.vjtech.gastoshogar.adapter.persistence.repository.IngresoRepository;
import com.vjtech.gastoshogar.adapter.persistence.repository.PresupuestoRepository;
import com.vjtech.gastoshogar.application.port.in.PresupuestoServicePort;
import com.vjtech.gastoshogar.adapter.rest.dto.presupuesto.IngresoResponse;
import com.vjtech.gastoshogar.adapter.rest.dto.presupuesto.IngresoUpdateRequest;
import com.vjtech.gastoshogar.adapter.rest.dto.presupuesto.PresupuestoRequest;
import com.vjtech.gastoshogar.adapter.rest.dto.presupuesto.PresupuestoResponse;
import com.vjtech.gastoshogar.adapter.rest.dto.presupuesto.ResumenResponse;
import com.vjtech.gastoshogar.domain.enums.ResumenEstado;
import org.modelmapper.ModelMapper;
import com.vjtech.gastoshogar.common.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Year;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PresupuestoServiceImpl implements PresupuestoServicePort {
    private final PresupuestoRepository presupuestoRepository;
    private final IngresoRepository ingresoRepository;
    private final GastoRepository gastoRepository;
    private final ImprevistoRepository imprevistoRepository;
    private final ModelMapper modelMapper;

    public PresupuestoServiceImpl(PresupuestoRepository presupuestoRepository, IngresoRepository ingresoRepository, GastoRepository gastoRepository, ImprevistoRepository imprevistoRepository, ModelMapper modelMapper) {
        this.presupuestoRepository = presupuestoRepository;
        this.ingresoRepository = ingresoRepository;
        this.gastoRepository = gastoRepository;
        this.imprevistoRepository = imprevistoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public PresupuestoResponse create(PresupuestoRequest req, UUID hogarId) {
        PresupuestoEntity entity = new PresupuestoEntity();
        entity.setHogarId(hogarId);
        entity.setAnio(req.getAnio());
        entity.setMes(req.getMes());
        entity.setEfectivoQ1(req.getEfectivoQ1());
        entity.setValesQ1(req.getValesQ1());
        entity.setNominaQ1(req.getNominaQ1());
        entity.setEfectivoQ2(req.getEfectivoQ2());
        entity.setValesQ2(req.getValesQ2());
        entity.setNominaQ2(req.getNominaQ2());
        PresupuestoEntity saved = presupuestoRepository.save(entity);
        return modelMapper.map(saved, PresupuestoResponse.class);
    }

    @Override
    @Transactional(readOnly = true)
    public PresupuestoResponse getByHogarId(UUID hogarId) {
        int mes = YearMonth.now().getMonthValue();
        int anio = Year.now().getValue();
        PresupuestoEntity entity = presupuestoRepository.findByHogarIdAndAnioAndMes(hogarId, anio, mes)
            .orElseGet(() -> {
                PresupuestoEntity e = new PresupuestoEntity();
                e.setHogarId(hogarId);
                e.setAnio(anio);
                e.setMes(mes);
                e.setEfectivoQ1(BigDecimal.ZERO);
                e.setValesQ1(BigDecimal.ZERO);
                e.setNominaQ1(BigDecimal.ZERO);
                e.setEfectivoQ2(BigDecimal.ZERO);
                e.setValesQ2(BigDecimal.ZERO);
                e.setNominaQ2(BigDecimal.ZERO);
                return presupuestoRepository.save(e);
            });
        return modelMapper.map(entity, PresupuestoResponse.class);
    }

    @Override
    @Transactional(readOnly = true)
    public ResumenResponse getSummary(UUID hogarId, int anio, int mes) {
        PresupuestoEntity presupuesto = presupuestoRepository.findByHogarIdAndAnioAndMes(hogarId, anio, mes)
            .orElseThrow(() -> new ResourceNotFoundException("Presupuesto no encontrado"));

        BigDecimal presupuestoTotal = presupuesto.getEfectivoQ1().add(presupuesto.getValesQ1()).add(presupuesto.getNominaQ1())
            .add(presupuesto.getEfectivoQ2()).add(presupuesto.getValesQ2()).add(presupuesto.getNominaQ2());

        List<IngresoEntity> ingresosQ1 = ingresoRepository.findByPresupuestoIdAndQuincena(presupuesto.getId(), "Q1");
        List<IngresoEntity> ingresosQ2 = ingresoRepository.findByPresupuestoIdAndQuincena(presupuesto.getId(), "Q2");

        List<GastoEntity> allGastos = gastoRepository.findByPresupuestoId(presupuesto.getId());
        List<GastoEntity> gastosPagados = gastoRepository.findByPresupuestoIdAndEstadoPago(presupuesto.getId(), "PAGADO");
        List<ImprevistoEntity> imprevistos = imprevistoRepository.findByPresupuestoId(presupuesto.getId());
        List<ImprevistoEntity> imprevistosPagados = imprevistos.stream()
            .filter(i -> "PAGADO".equals(i.getEstadoPago()))
            .collect(Collectors.toList());

        BigDecimal totalGastosPrevistos = BigDecimal.ZERO;
        for (GastoEntity g : allGastos) {
            if ("PENDIENTE".equals(g.getEstadoPago()) || "FIJO".equals(g.getTipo())) {
                totalGastosPrevistos = totalGastosPrevistos.add(g.getImporte());
            }
        }
        totalGastosPrevistos = totalGastosPrevistos.setScale(2, RoundingMode.HALF_UP);

        BigDecimal totalGastosReales = BigDecimal.ZERO;
        for (GastoEntity g : gastosPagados) {
            totalGastosReales = totalGastosReales.add(g.getImporte());
        }
        for (ImprevistoEntity imp : imprevistosPagados) {
            totalGastosReales = totalGastosReales.add(imp.getImporte());
        }
        totalGastosReales = totalGastosReales.setScale(2, RoundingMode.HALF_UP);

        BigDecimal saldoDisponible = presupuestoTotal.subtract(totalGastosReales);
        saldoDisponible = saldoDisponible.setScale(2, RoundingMode.HALF_UP);

        BigDecimal diferencia = presupuestoTotal.subtract(totalGastosPrevistos);
        diferencia = diferencia.setScale(2, RoundingMode.HALF_UP);

        String estado;
        BigDecimal importeDiferencia;
        if (diferencia.compareTo(BigDecimal.ZERO) == 0) {
            estado = ResumenEstado.TODO_BIEN.name();
            importeDiferencia = BigDecimal.ZERO;
        } else if (diferencia.compareTo(BigDecimal.ZERO) > 0) {
            estado = ResumenEstado.SOBRA_DINERO.name();
            importeDiferencia = diferencia;
        } else {
            estado = ResumenEstado.FALTA_DINERO.name();
            importeDiferencia = diferencia.abs();
        }

        List<IngresoResponse> ingresosQ1Responses = ingresosQ1.stream().map(e -> modelMapper.map(e, IngresoResponse.class)).collect(Collectors.toList());
        List<IngresoResponse> ingresosQ2Responses = ingresosQ2.stream().map(e -> modelMapper.map(e, IngresoResponse.class)).collect(Collectors.toList());

        return new ResumenResponse(presupuestoTotal, totalGastosPrevistos, totalGastosReales, saldoDisponible, diferencia, estado, importeDiferencia, ingresosQ1Responses, ingresosQ2Responses);
    }

    @Override
    @Transactional
    public PresupuestoResponse updateIngresos(UUID presupuestoId, String quincena, List<IngresoUpdateRequest> ingresos) {
        PresupuestoEntity presupuesto = presupuestoRepository.findById(presupuestoId)
            .orElseThrow(() -> new ResourceNotFoundException("Presupuesto no encontrado"));

        for (IngresoUpdateRequest req : ingresos) {
            IngresoEntity ingreso = new IngresoEntity();
            ingreso.setPresupuestoId(presupuestoId);
            ingreso.setQuincena(quincena);
            ingreso.setConcepto(req.getConcepto());
            ingreso.setMonto(req.getMonto());
            ingresoRepository.save(ingreso);
        }

        return modelMapper.map(presupuesto, PresupuestoResponse.class);
    }
}