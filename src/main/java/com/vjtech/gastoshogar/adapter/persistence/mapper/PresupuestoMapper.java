package com.vjtech.gastoshogar.adapter.persistence.mapper;

import com.vjtech.gastoshogar.adapter.persistence.entity.PresupuestoEntity;
import com.vjtech.gastoshogar.domain.model.Presupuesto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PresupuestoMapper implements Mapper<PresupuestoEntity, Presupuesto> {
    private final ModelMapper modelMapper;

    public PresupuestoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Presupuesto toDomain(PresupuestoEntity entity) {
        return modelMapper.map(entity, Presupuesto.class);
    }

    @Override
    public PresupuestoEntity toEntity(Presupuesto dto) {
        return modelMapper.map(dto, PresupuestoEntity.class);
    }

    @Override
    public void copyProperties(PresupuestoEntity source, Presupuesto target) {
        modelMapper.map(source, target);
    }
}