package com.bkmarriott.reservationservice.reservation.infrastructure.feignClient.adapter;

import com.bkmarriott.reservationservice.reservation.application.outputport.CouponOutputPort;
import org.springframework.stereotype.Component;

@Component
public class CouponAdapter implements CouponOutputPort {

    @Override
    public boolean useCoupon(Long couponId) {
        // TODO: openfeign client
        return true;
    }
}
