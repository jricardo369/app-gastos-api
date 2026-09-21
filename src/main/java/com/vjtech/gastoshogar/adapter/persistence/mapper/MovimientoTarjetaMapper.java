package com.vjtech.gastoshogar.adapter.persistence.mapper;

import com.vjtech.gastoshogar.adapter.persistence.entity.MovimientoTarjetaEntity;
import com.vjtech.gastoshogar.domain.model.MovimientoTarjeta;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MovimientoTarjetaMapper implements Mapper<MovimientoTarjetaEntity, MovimientoTarjeta> {
    private final ModelMapper modelMapper;

    public MovimientoTarjetaMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public MovimientoTarjeta toDomain(MovimientoTarjetaEntity entity) {
        return modelMapper.map(entity, MovimientoTarjeta.class);
    }

    @Override
    public MovimientoTarjetaEntity toEntity(MovimientoTarjeta dto) {
        return modelMapper.map(dto, MovimientoTarjetaEntity.class);
    }

    @Override
    public void copyProperties(MovimientoTarjetaEntity source, MovimientoTarjeta target) {
        modelMapper.map(source, target);
    }
}