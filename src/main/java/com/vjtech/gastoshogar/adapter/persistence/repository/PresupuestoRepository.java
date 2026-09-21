package com.vjtech.gastoshogar.adapter.persistence.repository;

import com.vjtech.gastoshogar.adapter.persistence.entity.PresupuestoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PresupuestoRepository extends JpaRepository<PresupuestoEntity, UUID>, JpaSpecificationExecutor<PresupuestoEntity> {
    List<PresupuestoEntity> findByHogarId(UUID hogarId);
    Optional<PresupuestoEntity> findByHogarIdAndAnioAndMes(UUID hogarId, int anio, int mes);
}