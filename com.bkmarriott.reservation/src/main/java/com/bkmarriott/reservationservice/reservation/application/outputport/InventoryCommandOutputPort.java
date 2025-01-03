package com.bkmarriott.reservationservice.reservation.application.outputport;

import com.bkmarriott.reservationservice.reservation.domain.Inventory;

public interface InventoryCommandOutputPort {

  Inventory increaseReserved(Inventory inventory);

  Inventory decreaseReserved(Inventory inventory);
}
