package com.bkmarriott.reservationservice.reservation.infrastructure.feignClient.adapter;

import com.bkmarriott.reservationservice.reservation.application.outputport.PaymentOutputPort;
import com.bkmarriott.reservationservice.reservation.infrastructure.feignClient.dto.Payment;
import com.bkmarriott.reservationservice.reservation.infrastructure.feignClient.dto.PaymentStatus;
import org.springframework.stereotype.Component;

@Component
public class PaymentAdapter implements PaymentOutputPort {

    public Payment getPayment(Long reservationId, Integer originalPrice, Integer finalPrice, Long appliedCouponId) {
        return new Payment(
                null,
                originalPrice,
                finalPrice,
                PaymentStatus.getRandomStatus()
        );
    }
}
