package com.bkmarriott.reservationservice.reservation.domain;

import com.bkmarriott.reservationservice.reservation.infrastructure.persistence.entity.RoomEntityType;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Inventory {

  private InventoryId id;
  private int totalInventory;
  private int totalReserved;

  @Builder
  public Inventory(InventoryId id, int totalInventory, int totalReserved) {
    this.id = id;
    this.totalInventory = totalInventory;
    this.totalReserved = totalReserved;
  }

  @Getter
  public static class InventoryId {

    private Long hotelId;
    private LocalDate date;
    private RoomEntityType roomType;

    @Builder
    public InventoryId(Long hotelId, LocalDate date, RoomEntityType roomType) {
      this.hotelId = hotelId;
      this.date = date;
      this.roomType = roomType;
    }
  }

}
