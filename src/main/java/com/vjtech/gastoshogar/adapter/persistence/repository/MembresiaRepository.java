package com.vjtech.gastoshogar.adapter.persistence.repository;

import com.vjtech.gastoshogar.adapter.persistence.entity.MembresiaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MembresiaRepository extends JpaRepository<MembresiaEntity, Long>, JpaSpecificationExecutor<MembresiaEntity> {
    List<MembresiaEntity> findByIdUserOrderByFechaInicioDesc(Long idUser);

    Optional<MembresiaEntity> findFirstByIdUserAndEstadoAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqualOrderByFechaInicioDesc(
            Long idUser, String estado, LocalDate inicio, LocalDate fin);
}