package com.bkmarriott.reservationservice.reservation.infrastructure.persistence.repository;

import static com.bkmarriott.reservationservice.reservation.infrastructure.persistence.entity.QRoomTypeInventoryEntity.*;

import com.bkmarriott.reservationservice.reservation.application.dto.InventoryQueryRequestDto;
import com.bkmarriott.reservationservice.reservation.infrastructure.persistence.entity.RoomTypeInventoryEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;

import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class InventoryQueryDslRepository {

  private final JPAQueryFactory queryFactory;

  public List<RoomTypeInventoryEntity> findAllInventoryByHotelIdAndDateRange(
      InventoryQueryRequestDto requestDto) {
    return queryFactory
            .selectFrom(roomTypeInventoryEntity)
            .setLockMode(LockModeType.OPTIMISTIC)
            .where(roomTypeInventoryEntity.id.hotelId.eq(requestDto.getHotelId())
                    .and(roomTypeInventoryEntity.id.date.between(requestDto.getStartDate(), requestDto.getEndDate())))
            .fetch();
  }
}
