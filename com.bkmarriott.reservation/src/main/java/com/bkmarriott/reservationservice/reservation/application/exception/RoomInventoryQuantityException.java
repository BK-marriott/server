package com.bkmarriott.reservationservice.reservation.application.exception;

public class RoomInventoryQuantityException extends RuntimeException {

    public RoomInventoryQuantityException(String message) {
        super(message);
    }
}
