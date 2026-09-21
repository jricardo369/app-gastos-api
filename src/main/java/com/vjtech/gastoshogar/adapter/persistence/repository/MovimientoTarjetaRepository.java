package com.vjtech.gastoshogar.adapter.persistence.repository;

import com.vjtech.gastoshogar.adapter.persistence.entity.MovimientoTarjetaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;
import java.util.UUID;

public interface MovimientoTarjetaRepository extends JpaRepository<MovimientoTarjetaEntity, UUID>, JpaSpecificationExecutor<MovimientoTarjetaEntity> {
    List<MovimientoTarjetaEntity> findByTarjetaId(UUID tarjetaId);
    List<MovimientoTarjetaEntity> findByTarjetaIdAndEstado(UUID tarjetaId, String estado);
    List<MovimientoTarjetaEntity> findByTarjetaIdAndTipo(UUID tarjetaId, String tipo);
    List<MovimientoTarjetaEntity> findByTarjetaIdAndEstadoAndTipo(UUID tarjetaId, String estado, String tipo);
}