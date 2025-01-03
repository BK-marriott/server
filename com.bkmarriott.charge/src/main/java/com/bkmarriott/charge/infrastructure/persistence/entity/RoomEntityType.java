package com.bkmarriott.charge.infrastructure.persistence.entity;

import com.bkmarriott.charge.domain.vo.RoomType;
import java.util.Objects;

public enum RoomEntityType {

    STANDARD, TWIN, DELUXE, SUITE;

    public RoomType toDomain() {
        for (RoomType roomType : RoomType.values()) {
            if (Objects.equals(roomType.name(), this.name())) {
                return roomType;
            }
        }
        throw new IllegalArgumentException("Invalid roomType: " + this.name());
    }

    public static RoomEntityType fromDomain(RoomType roomType) {
        return RoomEntityType.valueOf(roomType.name());
    }
}
