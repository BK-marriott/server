package com.bkmarriott.charge.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class RoomChargeForCreate {

    private final Long hotelId;
    private final RoomType roomType;
    private final Integer charge;
    private final LocalDate date;

    public static RoomChargeForCreate of(Long hotelId, RoomType roomType, Integer charge, LocalDate date) {
        return new RoomChargeForCreate(hotelId, roomType, charge, date);
    }
}
