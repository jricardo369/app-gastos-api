package com.vjtech.gastoshogar.adapter.persistence.mapper;

import com.vjtech.gastoshogar.adapter.persistence.entity.TarjetaEntity;
import com.vjtech.gastoshogar.domain.model.Tarjeta;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TarjetaMapper implements Mapper<TarjetaEntity, Tarjeta> {
    private final ModelMapper modelMapper;

    public TarjetaMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Tarjeta toDomain(TarjetaEntity entity) {
        return modelMapper.map(entity, Tarjeta.class);
    }

    @Override
    public TarjetaEntity toEntity(Tarjeta dto) {
        return modelMapper.map(dto, TarjetaEntity.class);
    }

    @Override
    public void copyProperties(TarjetaEntity source, Tarjeta target) {
        modelMapper.map(source, target);
    }
}