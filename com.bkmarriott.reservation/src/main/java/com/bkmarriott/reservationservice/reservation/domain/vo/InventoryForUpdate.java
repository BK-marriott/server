package com.bkmarriott.reservationservice.reservation.domain.vo;

import com.bkmarriott.reservationservice.reservation.infrastructure.persistence.entity.RoomEntityType;
import java.time.LocalDate;
import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class InventoryForUpdate {

  private Long hotelId;
  private LocalDate date;
  private RoomEntityType roomType;
  private int totalReserved;

}
