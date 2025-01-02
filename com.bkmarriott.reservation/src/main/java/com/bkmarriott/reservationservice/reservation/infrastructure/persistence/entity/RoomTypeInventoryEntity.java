package com.bkmarriott.reservationservice.reservation.infrastructure.persistence.entity;

import com.bkmarriott.reservationservice.reservation.domain.Inventory;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
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
}
