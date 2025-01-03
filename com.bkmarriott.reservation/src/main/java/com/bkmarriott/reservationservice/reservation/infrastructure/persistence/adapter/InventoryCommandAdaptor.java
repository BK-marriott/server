package com.bkmarriott.reservationservice.reservation.infrastructure.persistence.adapter;

import com.bkmarriott.reservationservice.reservation.application.outputport.InventoryCommandOutputPort;
import com.bkmarriott.reservationservice.reservation.domain.Inventory;
import com.bkmarriott.reservationservice.reservation.domain.vo.RoomType;
import com.bkmarriott.reservationservice.reservation.infrastructure.persistence.entity.RoomTypeInventoryEntity;
import com.bkmarriott.reservationservice.reservation.infrastructure.persistence.entity.RoomTypeInventoryId;
import com.bkmarriott.reservationservice.reservation.infrastructure.persistence.repository.InventoryRepository;
import com.bkmarriott.reservationservice.reservation.presentation.rest.exception.ResourceNotFoundException;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class InventoryCommandAdaptor implements InventoryCommandOutputPort {

  private final InventoryRepository inventoryRepository;

  public RoomTypeInventoryEntity findById(Long hotelId, LocalDate date,
      RoomType roomType) {
    return inventoryRepository.findById(RoomTypeInventoryId.from(hotelId,date,roomType))
        .orElseThrow(() -> new ResourceNotFoundException("존재하지 않는 인벤토리 정보"));
  }

  @Override
  public Inventory increaseReserved(Inventory inventory) {

    RoomTypeInventoryEntity roomTypeInventoryEntity = findById(inventory.getHotelId(), inventory.getDate(), inventory.getRoomType());

    return roomTypeInventoryEntity.increaseReserved().toDomain();
  }
}
