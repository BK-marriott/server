package com.bkmarriott.reservationservice.reservation.infrastructure.persistence.entity;

import com.bkmarriott.reservationservice.reservation.domain.Inventory;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "m_room_type_inventory")
@Entity
public class RoomTypeInventoryEntity extends BaseEntity {

  @EmbeddedId
  private RoomTypeInventoryId id;

  @Column(name = "total_inventory", nullable = false)
  private int totalInventory;

  @Column(name = "total_reserved", nullable = false)
  private int totalReserved;


  public Inventory toDomain() {
    return Inventory.builder()
        .id(id.toDomain())
        .totalInventory(totalInventory)
        .totalReserved(totalReserved)
        .build();
  }

  public RoomTypeInventoryEntity increaseReserved(Long hotelId, LocalDate date, RoomEntityType roomType, int totalInventory, int totalReserved) {
    return RoomTypeInventoryEntity.builder()
        .id(new RoomTypeInventoryId(hotelId, date, roomType))
        .totalInventory(totalInventory)
        .totalReserved(totalReserved + 1)
        .build();
  }

  public RoomTypeInventoryEntity decreaseReserved(Long hotelId, LocalDate date, RoomEntityType roomType, int totalInventory, int totalReserved) {
    return RoomTypeInventoryEntity.builder()
        .id(new RoomTypeInventoryId(hotelId, date, roomType))
        .totalInventory(totalInventory)
        .totalReserved(totalReserved - 1)
        .build();
  }
}
