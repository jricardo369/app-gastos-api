package com.vjtech.gastoshogar.adapter.persistence.repository;

import com.vjtech.gastoshogar.adapter.persistence.entity.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;
import java.util.UUID;

public interface CategoriaRepository extends JpaRepository<CategoriaEntity, UUID>, JpaSpecificationExecutor<CategoriaEntity> {
    List<CategoriaEntity> findByHogarId(UUID hogarId);
    List<CategoriaEntity> findByHogarIdAndEstado(UUID hogarId, String estado);
}