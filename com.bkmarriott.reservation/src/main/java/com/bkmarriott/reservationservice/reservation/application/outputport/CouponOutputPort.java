package com.bkmarriott.reservationservice.reservation.application.outputport;

public interface CouponOutputPort {
    boolean useCoupon(Long couponId);
}
