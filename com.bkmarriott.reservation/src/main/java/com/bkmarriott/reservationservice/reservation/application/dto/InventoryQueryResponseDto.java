package com.bkmarriott.reservationservice.reservation.application.dto;

import com.bkmarriott.reservationservice.reservation.domain.vo.RoomType;
import lombok.Getter;

@Getter
public class InventoryQueryResponseDto {

  private RoomType roomType;
  private int quantity;

  public InventoryQueryResponseDto(RoomType roomType, int quantity) {
    this.roomType = roomType;
    this.quantity = quantity;
  }
}
