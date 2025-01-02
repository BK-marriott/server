package com.bkmarriott.reservationservice.reservation.presentation.rest.dto.command;

import com.bkmarriott.reservationservice.reservation.domain.vo.InventoryForUpdate;
import com.bkmarriott.reservationservice.reservation.infrastructure.persistence.entity.RoomEntityType;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class InventoryModification {

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Request {

    private Long hotelId;
    private LocalDate startDate;
    private LocalDate endDate;
    private RoomEntityType roomType;

    public List<InventoryForUpdate> toDomain() {

      return getDateRange(this.startDate, this.endDate).stream()
          .map(date -> InventoryForUpdate.builder()
              .hotelId(hotelId)
              .date(date)
              .roomType(roomType)
              .build())
          .collect(Collectors.toList());
    }

    public static List<LocalDate> getDateRange(LocalDate startDate, LocalDate endDate) {

      return startDate.datesUntil(endDate.plusDays(1)) // endDate 포함
          .collect(Collectors.toList());
    }

  }

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor(access = AccessLevel.PRIVATE)
  @Builder
  public static class Response {

    private Long hotelId;
    private LocalDate date;
    private RoomEntityType roomType;
    private int totalInventory;
    private int totalReserved;

    public static Response fromDomain(Long hotelId, LocalDate date, RoomEntityType roomType, int totalInventory,int totalReserved) {
      return Response.builder()
          .hotelId(hotelId)
          .date(date)
          .roomType(roomType)
          .totalInventory(totalInventory)
          .totalReserved(totalReserved)
          .build();
    }
  }


}
