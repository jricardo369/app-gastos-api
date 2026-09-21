package com.vjtech.gastoshogar.application.service;

import com.vjtech.gastoshogar.adapter.persistence.entity.HogarEntity;
import com.vjtech.gastoshogar.adapter.persistence.repository.HogarRepository;
import com.vjtech.gastoshogar.application.port.in.HogarServicePort;
import com.vjtech.gastoshogar.adapter.rest.dto.hogar.HogarRequest;
import com.vjtech.gastoshogar.adapter.rest.dto.hogar.HogarResponse;
import org.modelmapper.ModelMapper;
import com.vjtech.gastoshogar.common.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class HogarServiceImpl implements HogarServicePort {
    private final HogarRepository hogarRepository;
    private final ModelMapper modelMapper;

    public HogarServiceImpl(HogarRepository hogarRepository, ModelMapper modelMapper) {
        this.hogarRepository = hogarRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public HogarResponse create(HogarRequest req, UUID userId) {
        HogarEntity entity = new HogarEntity();
        entity.setUserId(userId);
        entity.setNombre(req.getNombre());
        entity.setMoneda(req.getMoneda());
        entity.setZonaHoraria(req.getZonaHoraria());
        HogarEntity saved = hogarRepository.save(entity);
        return modelMapper.map(saved, HogarResponse.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HogarResponse> getAll(UUID userId) {
        return hogarRepository.findByUserId(userId).stream()
            .map(e -> modelMapper.map(e, HogarResponse.class))
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public HogarResponse getById(UUID id, UUID userId) {
        HogarEntity entity = hogarRepository.findByUserIdAndId(userId, id)
            .orElseThrow(() -> new ResourceNotFoundException("Hogar no encontrado"));
        return modelMapper.map(entity, HogarResponse.class);
    }

    @Override
    @Transactional
    public HogarResponse update(UUID id, HogarRequest req, UUID userId) {
        HogarEntity entity = hogarRepository.findByUserIdAndId(userId, id)
            .orElseThrow(() -> new ResourceNotFoundException("Hogar no encontrado"));
        entity.setNombre(req.getNombre());
        entity.setMoneda(req.getMoneda());
        entity.setZonaHoraria(req.getZonaHoraria());
        HogarEntity updated = hogarRepository.save(entity);
        return modelMapper.map(updated, HogarResponse.class);
    }

    @Override
    @Transactional
    public void delete(UUID id, UUID userId) {
        HogarEntity entity = hogarRepository.findByUserIdAndId(userId, id)
            .orElseThrow(() -> new ResourceNotFoundException("Hogar no encontrado"));
        hogarRepository.delete(entity);
    }
}