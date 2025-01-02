package com.bkmarriott.reservationservice.reservation.infrastructure.persistence.entity;

import com.bkmarriott.reservationservice.reservation.domain.Inventory.InventoryId;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
public class RoomTypeInventoryId implements Serializable {

  @Column(name = "hotel_id", unique = true, nullable = false)
  private Long hotelId;

  @Column(nullable = false)
  private LocalDate date;

  @Column(name = "room_type", nullable = false)
  @Enumerated(EnumType.STRING)
  private RoomEntityType roomType;

  @Builder
  public RoomTypeInventoryId(Long hotelId, LocalDate date,
      RoomEntityType roomType) {
    this.hotelId = hotelId;
    this.date = date;
    this.roomType = roomType;
  }

  public InventoryId toDomain() {
    return InventoryId.builder()
        .hotelId(this.hotelId)
        .date(this.date)
        .roomType(this.roomType)
        .build();
  }

  public static RoomTypeInventoryId fromDomain(InventoryId inventoryId) {
    return RoomTypeInventoryId.builder()
        .hotelId(inventoryId.getHotelId())
        .date(inventoryId.getDate())
        .roomType(inventoryId.getRoomType())
        .build();
  }
}
