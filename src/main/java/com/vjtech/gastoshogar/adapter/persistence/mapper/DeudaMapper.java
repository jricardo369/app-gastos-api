package com.vjtech.gastoshogar.adapter.persistence.mapper;

import com.vjtech.gastoshogar.adapter.persistence.entity.DeudaEntity;
import com.vjtech.gastoshogar.domain.model.Deuda;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DeudaMapper implements Mapper<DeudaEntity, Deuda> {
    private final ModelMapper modelMapper;

    public DeudaMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Deuda toDomain(DeudaEntity entity) {
        return modelMapper.map(entity, Deuda.class);
    }

    @Override
    public DeudaEntity toEntity(Deuda dto) {
        return modelMapper.map(dto, DeudaEntity.class);
    }

    @Override
    public void copyProperties(DeudaEntity source, Deuda target) {
        modelMapper.map(source, target);
    }
}