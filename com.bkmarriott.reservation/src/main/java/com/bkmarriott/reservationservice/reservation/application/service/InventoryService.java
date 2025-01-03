package com.bkmarriott.reservationservice.reservation.application.service;

import com.bkmarriott.reservationservice.reservation.application.exception.InventoryUpdateFailureException;
import com.bkmarriott.reservationservice.reservation.application.outputport.InventoryCommandOutputPort;
import com.bkmarriott.reservationservice.reservation.domain.Inventory;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryService {

  private final InventoryCommandOutputPort inventoryCommandOutputPort;

  public Inventory updateTotalReserved(Inventory inventoryForUpdate) {

    log.debug("[InventoryService] [updatetotalReserved] hotelId ::: {}, roomtype ::: {}", inventoryForUpdate.getHotelId(), inventoryForUpdate.getRoomType());

    if(inventoryForUpdate.getDate().isBefore(LocalDate.now())) {
      throw new IllegalArgumentException("예약 할 수 없는 날짜");
    }
    try {
      return inventoryCommandOutputPort.increaseReserved(inventoryForUpdate);
    } catch (Exception e) {
      log.error("[InventoryService] [increaseReserved] hotelId ::: {}, roomType ::: {}, date ::: {}",
          inventoryForUpdate.getHotelId(), inventoryForUpdate.getRoomType(), inventoryForUpdate.getDate());
      throw new InventoryUpdateFailureException("객실 예약 인벤토리 정보 수정 실패");
    }

  }
}
