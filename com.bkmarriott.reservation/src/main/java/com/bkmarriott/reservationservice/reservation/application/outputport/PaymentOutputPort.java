package com.bkmarriott.reservationservice.reservation.application.outputport;

import com.bkmarriott.reservationservice.reservation.infrastructure.feignClient.dto.Payment;

public interface PaymentOutputPort {
    Payment getPayment(Long reservationId, Integer originalPrice, Integer finalPrice, Long appliedCouponId);
}
