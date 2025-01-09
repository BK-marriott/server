package com.bkmarriott.reservationservice.reservation.domain.vo;

import lombok.Getter;

@Getter
public class InventoryQuantity {

  private RoomType roomType;
  private int quantity;

  public InventoryQuantity(RoomType roomType, int quantity) {
    this.roomType = roomType;
    this.quantity = quantity;
  }

  public boolean isQuantityEnough(int quantity) {
    return quantity >= this.quantity;
  }
}
