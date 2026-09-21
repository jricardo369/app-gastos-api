package com.vjtech.gastoshogar.adapter.persistence.repository;

import com.vjtech.gastoshogar.adapter.persistence.entity.ImprevistoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;
import java.util.UUID;

public interface ImprevistoRepository extends JpaRepository<ImprevistoEntity, UUID>, JpaSpecificationExecutor<ImprevistoEntity> {
    List<ImprevistoEntity> findByPresupuestoId(UUID presupuestoId);
    List<ImprevistoEntity> findByPresupuestoIdAndQuincena(UUID presupuestoId, String quincena);
}