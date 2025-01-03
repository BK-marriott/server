package com.bkmarriott.charge.presentation.rest.dto;

import com.bkmarriott.charge.domain.vo.RoomChargeForFind;
import com.bkmarriott.charge.domain.vo.RoomType;
import lombok.Getter;

import java.time.LocalDate;

public class FindRoomCharge {

    @Getter
    public static class Request {

        private Long hotelId;
        private RoomType roomType;
        private LocalDate date;

        public RoomChargeForFind toDomain() {
            return new RoomChargeForFind(hotelId, roomType, date);
        }
    }
}
