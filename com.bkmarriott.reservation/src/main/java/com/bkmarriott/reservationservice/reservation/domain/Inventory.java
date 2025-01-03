package com.bkmarriott.reservationservice.reservation.domain;

import com.bkmarriott.reservationservice.reservation.domain.vo.RoomType;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Inventory {

  private Long hotelId;
  private LocalDate date;
  private RoomType roomType;

  private int totalInventory;
  private int totalReserved;

  public static Inventory of(Long hotelId, LocalDate date, RoomType roomType, int totalInventory,int totalReserved) {
    return Inventory.builder()
        .hotelId(hotelId)
        .date(date)
        .roomType(roomType)
        .totalInventory(totalInventory)
        .totalReserved(totalReserved)
        .build();
  }



}
