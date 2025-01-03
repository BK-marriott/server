package com.bkmarriott.charge.domain;

import com.bkmarriott.charge.domain.vo.RoomType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class RoomCharge {

    private Long hotelId;
    private RoomType roomType;
    private LocalDate date;
    private Integer charge;

    public void updateCharge(Integer charge) {
        this.charge = charge;
    }
}
