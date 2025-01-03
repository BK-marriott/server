package com.bkmarriott.charge.domain.vo;

import java.time.LocalDate;

public record RoomChargeForCreate(Long hotelId, RoomType roomType, LocalDate date, Integer charge) {

    public static RoomChargeForCreate of(Long hotelId, RoomType roomType, LocalDate date, Integer charge) {
        return new RoomChargeForCreate(hotelId, roomType, date, charge);
    }
}
