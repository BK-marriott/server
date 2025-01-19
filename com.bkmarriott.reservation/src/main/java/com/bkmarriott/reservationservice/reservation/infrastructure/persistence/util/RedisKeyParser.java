package com.bkmarriott.reservationservice.reservation.infrastructure.persistence.util;

import com.bkmarriott.reservationservice.reservation.infrastructure.persistence.entity.RoomEntityType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

public class RedisKeyParser {
    public static ParsedKey parseInventoryKey(String redisRoomKey) {
        String[] parts = redisRoomKey.split(":");
        if (parts.length != 4 || !parts[0].equals("inventory")) {
            throw new IllegalArgumentException("Invalid redisRoomKey format: " + redisRoomKey);
        }

        Long hotelId = Long.parseLong(parts[1]);
        LocalDate date = LocalDate.parse(parts[2]);
        RoomEntityType roomType = RoomEntityType.valueOf(parts[3]);

        return new ParsedKey(hotelId, date, roomType);
    }

    @Getter
        @AllArgsConstructor
        public record ParsedKey(Long hotelId, LocalDate date, RoomEntityType roomType) {
    }
}
