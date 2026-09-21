package com.vjtech.gastoshogar.application.service;

import com.vjtech.gastoshogar.adapter.persistence.entity.DeudaEntity;
import com.vjtech.gastoshogar.adapter.persistence.entity.PagoDeudaEntity;
import com.vjtech.gastoshogar.adapter.persistence.repository.DeudaRepository;
import com.vjtech.gastoshogar.adapter.persistence.repository.PagoDeudaRepository;
import com.vjtech.gastoshogar.application.port.in.DeudaServicePort;
import com.vjtech.gastoshogar.adapter.rest.dto.deuda.DeudaRequest;
import com.vjtech.gastoshogar.adapter.rest.dto.deuda.DeudaResponse;
import com.vjtech.gastoshogar.adapter.rest.dto.deuda.PagoDeudaRequest;
import com.vjtech.gastoshogar.adapter.rest.dto.deuda.PagoDeudaResponse;
import org.modelmapper.ModelMapper;
import com.vjtech.gastoshogar.common.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DeudaServiceImpl implements DeudaServicePort {
    private final DeudaRepository deudaRepository;
    private final PagoDeudaRepository pagoDeudaRepository;
    private final ModelMapper modelMapper;

    public DeudaServiceImpl(DeudaRepository deudaRepository, PagoDeudaRepository pagoDeudaRepository, ModelMapper modelMapper) {
        this.deudaRepository = deudaRepository;
        this.pagoDeudaRepository = pagoDeudaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeudaResponse> getAll(UUID hogarId) {
        return deudaRepository.findByHogarId(hogarId).stream()
            .map(e -> modelMapper.map(e, DeudaResponse.class))
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public DeudaResponse create(DeudaRequest req, UUID hogarId) {
        DeudaEntity entity = new DeudaEntity();
        entity.setHogarId(hogarId);
        entity.setDeudor(req.getDeudor());
        entity.setDescripcion(req.getDescripcion());
        entity.setImporteTotal(req.getImporteTotal());
        entity.setImportePagado(BigDecimal.ZERO);
        entity.setSaldoPendiente(req.getImporteTotal());
        entity.setFechaVencimiento(req.getFechaVencimiento());
        entity.setEstado("ACTIVO");
        DeudaEntity saved = deudaRepository.save(entity);
        return modelMapper.map(saved, DeudaResponse.class);
    }

    @Override
    @Transactional
    public DeudaResponse update(UUID id, DeudaRequest req) {
        DeudaEntity entity = deudaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Deuda no encontrada"));
        entity.setDeudor(req.getDeudor());
        entity.setDescripcion(req.getDescripcion());
        entity.setImporteTotal(req.getImporteTotal());
        entity.setFechaVencimiento(req.getFechaVencimiento());
        entity.setSaldoPendiente(entity.getImporteTotal().subtract(entity.getImportePagado()));
        DeudaEntity updated = deudaRepository.save(entity);
        return modelMapper.map(updated, DeudaResponse.class);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        deudaRepository.deleteById(id);
    }

    @Override
    @Transactional
    public PagoDeudaResponse addPago(UUID deudaId, PagoDeudaRequest req) {
        DeudaEntity deuda = deudaRepository.findById(deudaId)
            .orElseThrow(() -> new ResourceNotFoundException("Deuda no encontrada"));

        if (req.getImporte().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResourceNotFoundException("El importe del pago debe ser mayor a cero");
        }

        BigDecimal saldoPendiente = deuda.getSaldoPendiente();
        if (req.getImporte().compareTo(saldoPendiente) > 0) {
            throw new ResourceNotFoundException("El importe del pago excede el saldo pendiente");
        }

        PagoDeudaEntity pago = new PagoDeudaEntity();
        pago.setDeudaId(deudaId);
        pago.setImporte(req.getImporte());
        pago.setFecha(req.getFecha());
        pagoDeudaRepository.save(pago);

        BigDecimal newImportePagado = deuda.getImportePagado().add(req.getImporte());
        BigDecimal newSaldoPendiente = deuda.getImporteTotal().subtract(newImportePagado);
        newSaldoPendiente = newSaldoPendiente.setScale(2, RoundingMode.HALF_UP);

        deuda.setImportePagado(newImportePagado);
        deuda.setSaldoPendiente(newSaldoPendiente);

        if (newSaldoPendiente.compareTo(BigDecimal.ZERO) <= 0) {
            deuda.setEstado("PAGADO");
            deuda.setSaldoPendiente(BigDecimal.ZERO);
        }

        DeudaEntity updated = deudaRepository.save(deuda);
        return modelMapper.map(pago, PagoDeudaResponse.class);
    }

    @Override
    @Transactional(readOnly = true)
    public DeudaResponse getSummary(UUID hogarId) {
        List<DeudaEntity> deudas = deudaRepository.findByHogarId(hogarId);
        if (deudas.isEmpty()) {
            throw new ResourceNotFoundException("No hay deudas registradas");
        }
        return modelMapper.map(deudas.get(0), DeudaResponse.class);
    }
}