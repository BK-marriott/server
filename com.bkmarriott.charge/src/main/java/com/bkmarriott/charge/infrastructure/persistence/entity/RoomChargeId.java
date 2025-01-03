package com.bkmarriott.charge.infrastructure.persistence.entity;

import com.bkmarriott.charge.domain.vo.RoomChargeForFind;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

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

    @Column(nullable = false)
    private LocalDate date;

    public static RoomChargeId fromDomain(RoomChargeForFind roomChargeForFind) {
        return new RoomChargeId(roomChargeForFind.hotelId(), RoomEntityType.fromDomain(roomChargeForFind.roomType()), roomChargeForFind.date());
    }
}
