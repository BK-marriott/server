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

    @Column(nullable = false)
    private LocalDate date;

    public RoomChargeEntity(Long hotelId, RoomEntityType roomType, Integer charge, LocalDate date) {
        this.id = new RoomChargeId(hotelId, roomType);
        this.charge = charge;
        this.date = date;
    }

    public RoomCharge toDomain() {
        return new RoomCharge(id.getHotelId(), id.getRoomType().toDomain(), charge, date);
    }

    public static RoomChargeEntity from(RoomChargeForCreate roomCharge) {
        return new RoomChargeEntity(
                roomCharge.getHotelId(),
                RoomEntityType.fromDomain(roomCharge.getRoomType()),
                roomCharge.getCharge(),
                roomCharge.getDate()
        );
    }
}
