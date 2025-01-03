package com.bkmarriott.charge.domain.vo;

import java.time.LocalDate;

public record RoomChargeForFind(Long hotelId, RoomType roomType, LocalDate date) {

    public static RoomChargeForFind of(Long hotelId, RoomType roomType, LocalDate date) {
        return new RoomChargeForFind(hotelId, roomType, date);
    }
}
