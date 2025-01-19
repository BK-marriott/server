package com.bkmarriott.reservationservice.reservation.infrastructure.persistence.repository;

import com.bkmarriott.reservationservice.reservation.infrastructure.persistence.entity.InventoryHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryHistoryRepository extends JpaRepository<InventoryHistoryEntity, Long> {

    List<InventoryHistoryEntity> findByRedisRoomKeyOrderBySequenceNumberAsc(String redisRoomKey);

}
