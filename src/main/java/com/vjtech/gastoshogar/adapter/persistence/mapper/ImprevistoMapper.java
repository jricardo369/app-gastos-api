package com.vjtech.gastoshogar.adapter.persistence.mapper;

import com.vjtech.gastoshogar.adapter.persistence.entity.ImprevistoEntity;
import com.vjtech.gastoshogar.domain.model.Imprevisto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ImprevistoMapper implements Mapper<ImprevistoEntity, Imprevisto> {
    private final ModelMapper modelMapper;

    public ImprevistoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Imprevisto toDomain(ImprevistoEntity entity) {
        return modelMapper.map(entity, Imprevisto.class);
    }

    @Override
    public ImprevistoEntity toEntity(Imprevisto dto) {
        return modelMapper.map(dto, ImprevistoEntity.class);
    }

    @Override
    public void copyProperties(ImprevistoEntity source, Imprevisto target) {
        modelMapper.map(source, target);
    }
}