package com.bkmarriott.reservationservice.reservation.domain.vo;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ReservationForCreate {

    private final Long guestId;
    private final Long hotelId;
    private final RoomType roomType;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final Integer amount;
    private final Integer originalPrice;
    private final Integer finalPrice;
    private final Long appliedCouponId;

    public ReservationForCreate(Long userId, Long hotelId, RoomType roomType, LocalDate startDate, LocalDate endDate, Integer amount, Integer originalPrice, Integer finalPrice, Long appliedCouponId) {
        this.guestId = userId;
        this.hotelId = hotelId;
        this.roomType = roomType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.originalPrice = originalPrice;
        this.finalPrice = finalPrice;
        this.appliedCouponId = appliedCouponId;
    }
}
