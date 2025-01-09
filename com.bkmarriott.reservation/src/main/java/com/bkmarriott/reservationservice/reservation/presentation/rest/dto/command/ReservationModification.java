package com.bkmarriott.reservationservice.reservation.presentation.rest.dto.command;

import com.bkmarriott.reservationservice.reservation.domain.Reservation;
import com.bkmarriott.reservationservice.reservation.domain.vo.ReservationForCreate;
import com.bkmarriott.reservationservice.reservation.domain.vo.ReservationStatus;
import com.bkmarriott.reservationservice.reservation.domain.vo.RoomType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class ReservationModification {

    @Getter
    @AllArgsConstructor
    public static class Request {

        private Long hotelId;
        private RoomType roomType;
        private LocalDate startDate;
        private LocalDate endDate;
        private Integer amount;
        private Integer originalPrice;
        private Integer finalPrice;
        private Long appliedCouponId;

        public ReservationForCreate toDomain(Long userId) {
            return new ReservationForCreate(
                    userId,
                    hotelId,
                    roomType,
                    startDate,
                    endDate,
                    amount,
                    originalPrice,
                    finalPrice,
                    appliedCouponId
            );
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Response {

        private Long reservationId;
        private Long userId;
        private Long hotelId;
        private LocalDate startDate;
        private LocalDate endDate;
        private RoomType roomType;
        private ReservationStatus status;

        public static Response from(Reservation reservation) {
            return new Response(
                    reservation.getReservationId(),
                    reservation.getUserId(),
                    reservation.getHotelId(),
                    reservation.getStartDate(),
                    reservation.getEndDate(),
                    reservation.getRoomType(),
                    reservation.getStatus());
        }
    }
}
