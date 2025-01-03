package com.bkmarriott.charge.infrastructure.persistence.entity;

import com.bkmarriott.charge.domain.RoomCharge;
import com.bkmarriott.charge.domain.vo.RoomChargeForCreate;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "m_room_charge")
@Entity
public class RoomChargeEntity extends BaseEntity {

    @EmbeddedId
    private RoomChargeId id;

    @Column(nullable = false)
    private Integer charge;

    public RoomChargeEntity(Long hotelId, RoomEntityType roomType, LocalDate date, Integer charge) {
        this.id = new RoomChargeId(hotelId, roomType, date);
        this.charge = charge;
    }

    public RoomCharge toDomain() {
        return new RoomCharge(id.getHotelId(), id.getRoomType().toDomain(), id.getDate(), charge);
    }

    public static RoomChargeEntity from(RoomChargeForCreate roomCharge) {
        return new RoomChargeEntity(
                roomCharge.hotelId(),
                RoomEntityType.fromDomain(roomCharge.roomType()),
                roomCharge.date(),
                roomCharge.charge()
        );
    }

    public static RoomChargeEntity fromDomain(RoomCharge roomCharge) {
        return new RoomChargeEntity(
                roomCharge.getHotelId(),
                RoomEntityType.fromDomain(roomCharge.getRoomType()),
                roomCharge.getDate(),
                roomCharge.getCharge()
        );
    }
}
