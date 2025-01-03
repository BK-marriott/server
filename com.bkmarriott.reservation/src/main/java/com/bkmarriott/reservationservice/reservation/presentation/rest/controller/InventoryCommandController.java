package com.bkmarriott.reservationservice.reservation.presentation.rest.controller;

import com.bkmarriott.reservationservice.reservation.application.service.InventoryService;
import com.bkmarriott.reservationservice.reservation.domain.Inventory;
import com.bkmarriott.reservationservice.reservation.presentation.rest.dto.command.InventoryModification.Request;
import com.bkmarriott.reservationservice.reservation.presentation.rest.dto.command.InventoryModification.Response;
import com.bkmarriott.reservationservice.reservation.presentation.rest.util.reponse.ApiResponse;
import com.bkmarriott.reservationservice.reservation.presentation.rest.util.reponse.ApiResponse.Success;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
  public ResponseEntity<Success<List<Response>>> updateInventory(@RequestBody Request request) {

    List<Inventory> inventoryList = request.toDomain();
    List<Response> responseList = inventoryList.stream()
        .map(inventoryService::updateTotalReserved)
        .map(inventory -> Response.from(
            inventory.getHotelId()
            ,inventory.getDate()
            ,inventory.getRoomType()
            ,inventory.getTotalInventory()
            ,inventory.getTotalReserved()))
        .collect(Collectors.toList());

    return ApiResponse.success(responseList, HttpStatus.OK);
  }
}
