package com.bkmarriott.charge.infrastructure.persistence.entity;

import com.bkmarriott.charge.domain.vo.RoomType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class RoomChargeId implements Serializable {

    @Column(nullable = false)
    private Long hotelId;

    @Enumerated(EnumType.STRING)
    private RoomEntityType roomType;

    public static RoomChargeId from(Long hotelId, RoomType roomType) {
        return new RoomChargeId(hotelId, RoomEntityType.fromDomain(roomType));
    }
}
