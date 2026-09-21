package com.vjtech.gastoshogar.adapter.persistence.mapper;

import com.vjtech.gastoshogar.adapter.persistence.entity.UsuarioEntity;
import com.vjtech.gastoshogar.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper implements Mapper<UsuarioEntity, Usuario> {
    private final ModelMapper modelMapper;

    public UsuarioMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public UsuarioEntity toEntity(Usuario dto) {
        return modelMapper.map(dto, UsuarioEntity.class);
    }

    @Override
    public Usuario toDomain(UsuarioEntity entity) {
        return modelMapper.map(entity, Usuario.class);
    }

    @Override
    public void copyProperties(UsuarioEntity source, Usuario target) {
        modelMapper.map(source, target);
    }
}