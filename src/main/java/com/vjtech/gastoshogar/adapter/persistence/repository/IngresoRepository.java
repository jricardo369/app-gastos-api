package com.vjtech.gastoshogar.adapter.persistence.repository;

import com.vjtech.gastoshogar.adapter.persistence.entity.IngresoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;
import java.util.UUID;

public interface IngresoRepository extends JpaRepository<IngresoEntity, UUID>, JpaSpecificationExecutor<IngresoEntity> {
    List<IngresoEntity> findByPresupuestoId(UUID presupuestoId);
    List<IngresoEntity> findByPresupuestoIdAndQuincena(UUID presupuestoId, String quincena);
}