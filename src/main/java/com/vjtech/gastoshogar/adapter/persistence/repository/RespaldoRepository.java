package com.vjtech.gastoshogar.adapter.persistence.repository;

import com.vjtech.gastoshogar.adapter.persistence.entity.RespaldoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RespaldoRepository extends JpaRepository<RespaldoEntity, Long> {
    Optional<RespaldoEntity> findByIdUser(Long idUser);
}
