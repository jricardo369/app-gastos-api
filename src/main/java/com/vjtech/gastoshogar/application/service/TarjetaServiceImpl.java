package com.vjtech.gastoshogar.application.service;

import com.vjtech.gastoshogar.adapter.persistence.entity.TarjetaEntity;
import com.vjtech.gastoshogar.adapter.persistence.entity.MovimientoTarjetaEntity;
import com.vjtech.gastoshogar.adapter.persistence.repository.TarjetaRepository;
import com.vjtech.gastoshogar.adapter.persistence.repository.MovimientoTarjetaRepository;
import com.vjtech.gastoshogar.application.port.in.TarjetaServicePort;
import com.vjtech.gastoshogar.adapter.rest.dto.tarjeta.MovimientoTarjetaResponse;
import com.vjtech.gastoshogar.adapter.rest.dto.tarjeta.TarjetaRequest;
import com.vjtech.gastoshogar.adapter.rest.dto.tarjeta.TarjetaResponse;
import org.modelmapper.ModelMapper;
import com.vjtech.gastoshogar.common.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TarjetaServiceImpl implements TarjetaServicePort {
    private final TarjetaRepository tarjetaRepository;
    private final MovimientoTarjetaRepository movimientoTarjetaRepository;
    private final ModelMapper modelMapper;

    public TarjetaServiceImpl(TarjetaRepository tarjetaRepository, MovimientoTarjetaRepository movimientoTarjetaRepository, ModelMapper modelMapper) {
        this.tarjetaRepository = tarjetaRepository;
        this.movimientoTarjetaRepository = movimientoTarjetaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TarjetaResponse> getAll(UUID hogarId) {
        return tarjetaRepository.findByHogarId(hogarId).stream()
            .map(e -> modelMapper.map(e, TarjetaResponse.class))
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TarjetaResponse create(TarjetaRequest req, UUID hogarId) {
        TarjetaEntity entity = new TarjetaEntity();
        entity.setHogarId(hogarId);
        entity.setTitular(req.getTitular());
        entity.setNumero(req.getNumero());
        entity.setBanco(req.getBanco());
        entity.setEstado("ACTIVO");
        TarjetaEntity saved = tarjetaRepository.save(entity);
        return modelMapper.map(saved, TarjetaResponse.class);
    }

    @Override
    @Transactional(readOnly = true)
    public TarjetaResponse getById(UUID id, UUID hogarId) {
        TarjetaEntity entity = tarjetaRepository.findByHogarId(hogarId).stream()
            .filter(t -> t.getId().equals(id))
            .findFirst()
            .orElseThrow(() -> new ResourceNotFoundException("Tarjeta no encontrada"));
        return modelMapper.map(entity, TarjetaResponse.class);
    }

    @Override
    @Transactional
    public TarjetaResponse update(UUID id, TarjetaRequest req) {
        TarjetaEntity entity = tarjetaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Tarjeta no encontrada"));
        entity.setTitular(req.getTitular());
        entity.setNumero(req.getNumero());
        entity.setBanco(req.getBanco());
        TarjetaEntity updated = tarjetaRepository.save(entity);
        return modelMapper.map(updated, TarjetaResponse.class);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        tarjetaRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovimientoTarjetaResponse> getMovimientos(UUID tarjetaId, String estado, String tipo) {
        List<MovimientoTarjetaEntity> movimientos;
        if (estado != null && tipo != null) {
            movimientos = movimientoTarjetaRepository.findByTarjetaIdAndEstadoAndTipo(tarjetaId, estado, tipo);
        } else if (estado != null) {
            movimientos = movimientoTarjetaRepository.findByTarjetaIdAndEstado(tarjetaId, estado);
        } else if (tipo != null) {
            movimientos = movimientoTarjetaRepository.findByTarjetaIdAndTipo(tarjetaId, tipo);
        } else {
            movimientos = movimientoTarjetaRepository.findByTarjetaId(tarjetaId);
        }
        return movimientos.stream().map(e -> modelMapper.map(e, MovimientoTarjetaResponse.class)).collect(Collectors.toList());
    }
}