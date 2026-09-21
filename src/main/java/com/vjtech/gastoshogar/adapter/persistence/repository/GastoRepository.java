package com.vjtech.gastoshogar.adapter.persistence.repository;

import com.vjtech.gastoshogar.adapter.persistence.entity.GastoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;
import java.util.UUID;

public interface GastoRepository extends JpaRepository<GastoEntity, UUID>, JpaSpecificationExecutor<GastoEntity> {
    List<GastoEntity> findByPresupuestoId(UUID presupuestoId);
    List<GastoEntity> findByPresupuestoIdAndQuincena(UUID presupuestoId, String quincena);
    List<GastoEntity> findByPresupuestoIdAndEstadoPago(UUID presupuestoId, String estadoPago);
}