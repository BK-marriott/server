package com.bkmarriott.reservationservice.reservation.application.exception;

public class PaymentFailureException extends RuntimeException {

    public PaymentFailureException(String message) {
        super(message);
    }
}
