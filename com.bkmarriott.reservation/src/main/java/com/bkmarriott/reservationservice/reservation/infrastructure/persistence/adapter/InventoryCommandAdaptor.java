package com.bkmarriott.reservationservice.reservation.infrastructure.persistence.adapter;

import com.bkmarriott.reservationservice.reservation.application.outputport.InventoryCommandOutputPort;
import com.bkmarriott.reservationservice.reservation.domain.Inventory;
import com.bkmarriott.reservationservice.reservation.infrastructure.persistence.entity.RoomTypeInventoryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class InventoryCommandAdaptor implements InventoryCommandOutputPort {

  private final InventoryQueryAdaptor inventoryQueryAdaptor;

  @Override
  public Inventory increaseReserved(Inventory inventory) {
    RoomTypeInventoryEntity roomTypeInventoryEntity = inventoryQueryAdaptor
        .findById(inventory.getHotelId(), inventory.getDate(), inventory.getRoomType());
    return roomTypeInventoryEntity.increaseReserved().toDomain();
  }

  @Override
  public Inventory decreaseReserved(Inventory inventory) {
    RoomTypeInventoryEntity roomTypeInventoryEntity = inventoryQueryAdaptor
        .findById(inventory.getHotelId(), inventory.getDate(), inventory.getRoomType());
    return roomTypeInventoryEntity.decreaseReserved().toDomain();
  }
}
