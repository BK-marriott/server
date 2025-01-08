package com.bkmarriott.reservationservice.reservation.infrastructure.persistence.dto;

import com.bkmarriott.reservationservice.reservation.application.dto.InventoryQueryResponseDto;
import com.bkmarriott.reservationservice.reservation.infrastructure.persistence.entity.RoomEntityType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class InventoryQuery {

    private RoomEntityType roomType;
    private int quantity;

    @QueryProjection
    public InventoryQuery(RoomEntityType roomType, int quantity) {
        this.roomType = roomType;
        this.quantity = quantity;
    }

    public InventoryQueryResponseDto toDto() {
        return new InventoryQueryResponseDto(roomType.toDomain(), quantity);
    }
}

