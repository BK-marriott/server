package com.bkmarriott.reservationservice.reservation.application.service;

import com.bkmarriott.reservationservice.reservation.domain.Inventory;
import com.bkmarriott.reservationservice.reservation.domain.Inventory.InventoryId;
import com.bkmarriott.reservationservice.reservation.domain.vo.InventoryForUpdate;
import com.bkmarriott.reservationservice.reservation.infrastructure.persistence.adapter.InventoryCommandAdaptor;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class InventoryService {

  private final InventoryCommandAdaptor inventoryCommandAdaptor;

  public List<Inventory> updatetotalReserved(List<InventoryForUpdate> inventoryForUpdates) {

    log.debug("[InventoryService] [updatetotalReserved] hotelId ::: {}, roomtype ::: {}", inventoryForUpdates.get(0).getHotelId(), inventoryForUpdates.get(0).getRoomType());

    List<Inventory> inventoryList = new ArrayList<>();

    for(InventoryForUpdate inventoryForUpdate : inventoryForUpdates) {
      Inventory inventory = inventoryCommandAdaptor.findById(
          new InventoryId(
              inventoryForUpdate.getHotelId(),
              inventoryForUpdate.getDate(),
              inventoryForUpdate.getRoomType()))
          .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

      inventoryList.add(inventory);
      // update
    }

    return inventoryList;
  }
}
