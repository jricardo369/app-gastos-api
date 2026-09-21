package com.vjtech.gastoshogar.adapter.persistence.mapper;

import com.vjtech.gastoshogar.adapter.persistence.entity.HogarEntity;
import com.vjtech.gastoshogar.domain.model.Hogar;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class HogarMapper implements Mapper<HogarEntity, Hogar> {
    private final ModelMapper modelMapper;

    public HogarMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Hogar toDomain(HogarEntity entity) {
        return modelMapper.map(entity, Hogar.class);
    }

    @Override
    public HogarEntity toEntity(Hogar dto) {
        return modelMapper.map(dto, HogarEntity.class);
    }

    @Override
    public void copyProperties(HogarEntity source, Hogar target) {
        modelMapper.map(source, target);
    }
}