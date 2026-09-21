package com.vjtech.gastoshogar.application.service;

import com.vjtech.gastoshogar.adapter.persistence.entity.CategoriaEntity;
import com.vjtech.gastoshogar.adapter.persistence.repository.CategoriaRepository;
import com.vjtech.gastoshogar.application.port.in.CategoriaServicePort;
import com.vjtech.gastoshogar.adapter.rest.dto.categoria.CategoriaRequest;
import com.vjtech.gastoshogar.adapter.rest.dto.categoria.CategoriaResponse;
import org.modelmapper.ModelMapper;
import com.vjtech.gastoshogar.common.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoriaServiceImpl implements CategoriaServicePort {
    private final CategoriaRepository categoriaRepository;
    private final ModelMapper modelMapper;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository, ModelMapper modelMapper) {
        this.categoriaRepository = categoriaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaResponse> getAll(UUID hogarId) {
        return categoriaRepository.findByHogarId(hogarId).stream()
            .map(e -> modelMapper.map(e, CategoriaResponse.class))
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CategoriaResponse create(CategoriaRequest req, UUID hogarId) {
        CategoriaEntity entity = new CategoriaEntity();
        entity.setHogarId(hogarId);
        entity.setNombre(req.getNombre());
        entity.setColor(req.getColor());
        entity.setIcono(req.getIcono());
        entity.setEstado(req.getEstado());
        CategoriaEntity saved = categoriaRepository.save(entity);
        return modelMapper.map(saved, CategoriaResponse.class);
    }

    @Override
    @Transactional
    public CategoriaResponse update(UUID id, CategoriaRequest req, UUID hogarId) {
        CategoriaEntity entity = categoriaRepository.findByHogarId(hogarId).stream()
            .filter(e -> e.getId().equals(id))
            .findFirst()
            .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
        entity.setNombre(req.getNombre());
        entity.setColor(req.getColor());
        entity.setIcono(req.getIcono());
        entity.setEstado(req.getEstado());
        CategoriaEntity updated = categoriaRepository.save(entity);
        return modelMapper.map(updated, CategoriaResponse.class);
    }

    @Override
    @Transactional
    public void delete(UUID id, UUID hogarId) {
        CategoriaEntity entity = categoriaRepository.findByHogarId(hogarId).stream()
            .filter(e -> e.getId().equals(id))
            .findFirst()
            .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
        categoriaRepository.delete(entity);
    }
}