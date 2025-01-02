package com.bkmarriott.charge.application.outputport;

import com.bkmarriott.charge.domain.RoomCharge;
import com.bkmarriott.charge.domain.vo.RoomChargeForCreate;
import com.bkmarriott.charge.domain.vo.RoomChargeForFind;

import java.util.Optional;

public interface RoomChargeOutputPort {

    Optional<RoomCharge> findById(RoomChargeForFind roomChargeForFind);

    RoomCharge create(RoomChargeForCreate roomChargeForCreate);
}
