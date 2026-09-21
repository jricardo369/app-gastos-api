package com.vjtech.gastoshogar.adapter.persistence.mapper;

import com.vjtech.gastoshogar.adapter.persistence.entity.CategoriaEntity;
import com.vjtech.gastoshogar.domain.model.Categoria;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper implements Mapper<CategoriaEntity, Categoria> {
    private final ModelMapper modelMapper;

    public CategoriaMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Categoria toDomain(CategoriaEntity entity) {
        return modelMapper.map(entity, Categoria.class);
    }

    @Override
    public CategoriaEntity toEntity(Categoria dto) {
        return modelMapper.map(dto, CategoriaEntity.class);
    }

    @Override
    public void copyProperties(CategoriaEntity source, Categoria target) {
        modelMapper.map(source, target);
    }
}