package com.vjtech.gastoshogar.application.service;

import com.vjtech.gastoshogar.adapter.persistence.entity.GastoEntity;
import com.vjtech.gastoshogar.adapter.persistence.repository.GastoRepository;
import com.vjtech.gastoshogar.application.port.in.GastoServicePort;
import com.vjtech.gastoshogar.adapter.rest.dto.gasto.GastoFilter;
import com.vjtech.gastoshogar.adapter.rest.dto.gasto.GastoRequest;
import com.vjtech.gastoshogar.adapter.rest.dto.gasto.GastoResponse;
import org.modelmapper.ModelMapper;
import com.vjtech.gastoshogar.common.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GastoServiceImpl implements GastoServicePort {
    private final GastoRepository gastoRepository;
    private final ModelMapper modelMapper;

    public GastoServiceImpl(GastoRepository gastoRepository, ModelMapper modelMapper) {
        this.gastoRepository = gastoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<GastoResponse> getAll(UUID presupuestoId, GastoFilter filter) {
        List<GastoEntity> entities = gastoRepository.findByPresupuestoId(presupuestoId);
        return entities.stream().map(e -> modelMapper.map(e, GastoResponse.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public GastoResponse create(UUID presupuestoId, GastoRequest req) {
        GastoEntity entity = new GastoEntity();
        entity.setPresupuestoId(presupuestoId);
        entity.setQuincena(req.getQuincena());
        entity.setCategoriaId(java.util.UUID.fromString(req.getCategoriaId()));
        entity.setDescripcion(req.getDescripcion());
        entity.setImporte(req.getImporte());
        entity.setFecha(req.getFecha());
        entity.setTipo(req.getTipo());
        entity.setEstadoPago(req.getEstadoPago());
        entity.setObservaciones(req.getObservaciones());
        GastoEntity saved = gastoRepository.save(entity);
        return modelMapper.map(saved, GastoResponse.class);
    }

    @Override
    @Transactional
    public GastoResponse update(UUID id, GastoRequest req) {
        GastoEntity entity = gastoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Gasto no encontrado"));
        entity.setDescripcion(req.getDescripcion());
        entity.setImporte(req.getImporte());
        entity.setCategoriaId(java.util.UUID.fromString(req.getCategoriaId()));
        entity.setFecha(req.getFecha());
        entity.setTipo(req.getTipo());
        entity.setEstadoPago(req.getEstadoPago());
        entity.setObservaciones(req.getObservaciones());
        GastoEntity updated = gastoRepository.save(entity);
        return modelMapper.map(updated, GastoResponse.class);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        gastoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public GastoResponse togglePaid(UUID id) {
        GastoEntity entity = gastoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Gasto no encontrado"));
        if ("PAGADO".equals(entity.getEstadoPago())) {
            entity.setEstadoPago("PENDIENTE");
        } else {
            entity.setEstadoPago("PAGADO");
        }
        GastoEntity updated = gastoRepository.save(entity);
        return modelMapper.map(updated, GastoResponse.class);
    }

    @Override
    @Transactional
    public GastoResponse markAsPaid(UUID id) {
        GastoEntity entity = gastoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Gasto no encontrado"));
        entity.setEstadoPago("PAGADO");
        GastoEntity updated = gastoRepository.save(entity);
        return modelMapper.map(updated, GastoResponse.class);
    }
}