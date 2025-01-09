package com.bkmarriott.reservationservice.reservation.infrastructure.feignClient.dto;

import com.bkmarriott.reservationservice.reservation.domain.vo.ReservationStatus;

import java.util.List;
import java.util.Random;

public enum PaymentStatus {
    PENDING, PAID, REFUNDED, CANCELLED, REJECTED;

    private static List<PaymentStatus> initialStatus() {
        return List.of(PENDING, PAID, REJECTED);
    }

    public static PaymentStatus getRandomStatus() {
        List<PaymentStatus> status = initialStatus();
        Random random = new Random();
        return status.get(random.nextInt(status.size()));
    }

    public boolean isInitialFail() {
        return this != PAID;
    }

    public ReservationStatus toReservationStatus() {
        return ReservationStatus.valueOf(this.name());
    }
}
