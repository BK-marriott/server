package com.bkmarriott.charge.infrastructure.persistence.repository;

import com.bkmarriott.charge.infrastructure.persistence.entity.RoomChargeEntity;
import com.bkmarriott.charge.infrastructure.persistence.entity.RoomChargeId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomChargeRepository extends JpaRepository<RoomChargeEntity, RoomChargeId> {
    Optional<RoomChargeEntity> findByIdAndIsDeletedFalse(RoomChargeId id);
}
