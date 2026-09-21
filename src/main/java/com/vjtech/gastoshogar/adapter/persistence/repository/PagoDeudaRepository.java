package com.vjtech.gastoshogar.adapter.persistence.repository;

import com.vjtech.gastoshogar.adapter.persistence.entity.PagoDeudaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;
import java.util.UUID;

public interface PagoDeudaRepository extends JpaRepository<PagoDeudaEntity, UUID>, JpaSpecificationExecutor<PagoDeudaEntity> {
    List<PagoDeudaEntity> findByDeudaId(UUID deudaId);
}