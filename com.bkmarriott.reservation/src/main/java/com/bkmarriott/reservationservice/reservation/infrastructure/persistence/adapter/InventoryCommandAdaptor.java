package com.bkmarriott.reservationservice.reservation.infrastructure.persistence.adapter;

import com.bkmarriott.reservationservice.reservation.domain.Inventory;
import com.bkmarriott.reservationservice.reservation.domain.Inventory.InventoryId;
import com.bkmarriott.reservationservice.reservation.infrastructure.persistence.entity.RoomEntityType;
import com.bkmarriott.reservationservice.reservation.infrastructure.persistence.entity.RoomTypeInventoryEntity;
import com.bkmarriott.reservationservice.reservation.infrastructure.persistence.entity.RoomTypeInventoryId;
import com.bkmarriott.reservationservice.reservation.infrastructure.persistence.repository.InventoryRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class InventoryCommandAdaptor {

  private final InventoryRepository inventoryRepository;

  public Optional<Inventory> findById(InventoryId inventoryId) {
    return inventoryRepository.findById(RoomTypeInventoryId.fromDomain(inventoryId))
        .map(RoomTypeInventoryEntity::toDomain)
        .or(Optional::empty);
  }

  public void increaseReservated(Inventory inventory) {
    inventoryRepository.findById(RoomTypeInventoryId.fromDomain(inventory.getId()))
        .ifPresent(entity -> new RoomTypeInventoryEntity().increaseReserved(
            inventory.getId().getHotelId(), inventory.getId().getDate(), inventory.getId()
                .getRoomType(), inventory.getTotalInventory(), inventory.getTotalReserved()
        ));
  }
}
