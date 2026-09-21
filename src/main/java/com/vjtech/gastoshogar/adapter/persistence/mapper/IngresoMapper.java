package com.vjtech.gastoshogar.adapter.persistence.mapper;

import com.vjtech.gastoshogar.adapter.persistence.entity.IngresoEntity;
import com.vjtech.gastoshogar.domain.model.Ingreso;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class IngresoMapper implements Mapper<IngresoEntity, Ingreso> {
    private final ModelMapper modelMapper;

    public IngresoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Ingreso toDomain(IngresoEntity entity) {
        return modelMapper.map(entity, Ingreso.class);
    }

    @Override
    public IngresoEntity toEntity(Ingreso dto) {
        return modelMapper.map(dto, IngresoEntity.class);
    }

    @Override
    public void copyProperties(IngresoEntity source, Ingreso target) {
        modelMapper.map(source, target);
    }
}