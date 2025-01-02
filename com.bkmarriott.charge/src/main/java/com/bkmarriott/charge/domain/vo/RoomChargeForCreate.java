package com.bkmarriott.charge.domain.vo;

import java.time.LocalDate;

public record RoomChargeForCreate(Long hotelId, RoomType roomType, Integer charge, LocalDate date) {

    public static RoomChargeForCreate of(Long hotelId, RoomType roomType, Integer charge, LocalDate date) {
        return new RoomChargeForCreate(hotelId, roomType, charge, date);
    }
}
