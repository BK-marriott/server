package com.bkmarriott.reservationservice.reservation.infrastructure.feignClient.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Payment {
    private Long paymentId;
    private Integer originalPrice;
    private Integer finalPrice;
    private PaymentStatus paymentStatus;

    public boolean isFailed() {
        return this.paymentStatus.isInitialFail();
    }
}
