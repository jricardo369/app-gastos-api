package com.vjtech.gastoshogar.adapter.persistence.mapper;

public interface Mapper<E, D> {
    E toEntity(D dto);
    D toDomain(E entity);
    void copyProperties(E source, D target);
}