package com.bkmarriott.charge.domain.vo;

public record RoomChargeForFind(Long hotelId, RoomType roomType) {

    public static RoomChargeForFind of(Long hotelId, RoomType roomType) {
        return new RoomChargeForFind(hotelId, roomType);
    }
}
