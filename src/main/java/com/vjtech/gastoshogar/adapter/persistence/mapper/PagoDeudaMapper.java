package com.vjtech.gastoshogar.adapter.persistence.mapper;

import com.vjtech.gastoshogar.adapter.persistence.entity.PagoDeudaEntity;
import com.vjtech.gastoshogar.domain.model.PagoDeuda;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PagoDeudaMapper implements Mapper<PagoDeudaEntity, PagoDeuda> {
    private final ModelMapper modelMapper;

    public PagoDeudaMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public PagoDeuda toDomain(PagoDeudaEntity entity) {
        return modelMapper.map(entity, PagoDeuda.class);
    }

    @Override
    public PagoDeudaEntity toEntity(PagoDeuda dto) {
        return modelMapper.map(dto, PagoDeudaEntity.class);
    }

    @Override
    public void copyProperties(PagoDeudaEntity source, PagoDeuda target) {
        modelMapper.map(source, target);
    }
}