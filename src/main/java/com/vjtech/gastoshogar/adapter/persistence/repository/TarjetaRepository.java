package com.vjtech.gastoshogar.adapter.persistence.repository;

import com.vjtech.gastoshogar.adapter.persistence.entity.TarjetaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;
import java.util.UUID;

public interface TarjetaRepository extends JpaRepository<TarjetaEntity, UUID>, JpaSpecificationExecutor<TarjetaEntity> {
    List<TarjetaEntity> findByHogarId(UUID hogarId);
}