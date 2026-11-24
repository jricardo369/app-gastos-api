package com.vjtech.gastoshogar.adapter.persistence.repository;

import com.vjtech.gastoshogar.adapter.persistence.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long>, JpaSpecificationExecutor<UsuarioEntity> {
    Optional<UsuarioEntity> findByUser(String user);
    Optional<UsuarioEntity> findByEmail(String email);
    boolean existsByUser(String user);
    boolean existsByEmail(String email);
}