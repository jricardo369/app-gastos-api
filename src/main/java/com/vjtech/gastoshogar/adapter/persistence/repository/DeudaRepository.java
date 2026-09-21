package com.vjtech.gastoshogar.adapter.persistence.repository;

import com.vjtech.gastoshogar.adapter.persistence.entity.DeudaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;
import java.util.UUID;

public interface DeudaRepository extends JpaRepository<DeudaEntity, UUID>, JpaSpecificationExecutor<DeudaEntity> {
    List<DeudaEntity> findByHogarId(UUID hogarId);
}