package com.bkmarriott.reservationservice.reservation.presentation.rest.controller;

import com.bkmarriott.reservationservice.reservation.application.service.InventoryService;
import com.bkmarriott.reservationservice.reservation.domain.Inventory;
import com.bkmarriott.reservationservice.reservation.domain.vo.InventoryForUpdate;
import com.bkmarriott.reservationservice.reservation.presentation.rest.dto.command.InventoryModification.Request;
import com.bkmarriott.reservationservice.reservation.presentation.rest.util.reponse.ApiResponse.Success;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reservation/inventory/")
public class InventoryCommandController {

  private final InventoryService inventoryService;

  @PatchMapping
  public ResponseEntity<Success<Void>> updateInventory(@RequestBody Request request) {

    List<InventoryForUpdate> inventoryForUpdates = request.toDomain();
    List<Inventory> inventoryList = inventoryService.updatetotalReserved(inventoryForUpdates);

//    Response response = Response.from(inventory);
//    return ApiResponse.success(response, HttpStatus.OK);
  }
}
