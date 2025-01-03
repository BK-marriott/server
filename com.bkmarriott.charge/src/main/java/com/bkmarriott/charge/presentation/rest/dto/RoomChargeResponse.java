package com.bkmarriott.charge.presentation.rest.dto;

import com.bkmarriott.charge.domain.RoomCharge;
import com.bkmarriott.charge.domain.vo.RoomType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class RoomChargeResponse {

    private Long hotelId;
    private RoomType roomType;
    private Integer charge;
    private LocalDate date;

    public static RoomChargeResponse from(RoomCharge roomCharge) {
        return new RoomChargeResponse(
                roomCharge.getHotelId(),
                roomCharge.getRoomType(),
                roomCharge.getCharge(),
                roomCharge.getDate()
        );
    }
}
