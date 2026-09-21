package com.vjtech.gastoshogar.adapter.persistence.mapper;

import com.vjtech.gastoshogar.adapter.persistence.entity.GastoEntity;
import com.vjtech.gastoshogar.domain.model.Gasto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class GastoMapper implements Mapper<GastoEntity, Gasto> {
    private final ModelMapper modelMapper;

    public GastoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Gasto toDomain(GastoEntity entity) {
        return modelMapper.map(entity, Gasto.class);
    }

    @Override
    public GastoEntity toEntity(Gasto dto) {
        return modelMapper.map(dto, GastoEntity.class);
    }

    @Override
    public void copyProperties(GastoEntity source, Gasto target) {
        modelMapper.map(source, target);
    }
}