package com.vjtech.gastoshogar.adapter.persistence.repository;

import com.vjtech.gastoshogar.adapter.persistence.entity.HogarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HogarRepository extends JpaRepository<HogarEntity, UUID>, JpaSpecificationExecutor<HogarEntity> {
    List<HogarEntity> findByUserId(UUID userId);
    Optional<HogarEntity> findByUserIdAndId(UUID userId, UUID id);
}