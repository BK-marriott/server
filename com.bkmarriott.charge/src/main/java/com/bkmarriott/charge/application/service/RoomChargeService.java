package com.bkmarriott.charge.application.service;

import com.bkmarriott.charge.application.exception.RoomChargeDuplicatedException;
import com.bkmarriott.charge.application.exception.RoomChargeNotFoundException;
import com.bkmarriott.charge.application.outputport.RoomChargeOutputPort;
import com.bkmarriott.charge.domain.RoomCharge;
import com.bkmarriott.charge.domain.vo.RoomChargeForCreate;
import com.bkmarriott.charge.domain.vo.RoomChargeForFind;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class RoomChargeService {

    private final RoomChargeOutputPort roomChargeOutputPort;

    @Transactional
    public RoomCharge create(RoomChargeForCreate roomChargeForCreate) {
        log.debug("[RoomChargeService] [create] hotelId ::: {}, roomType ::: {}", roomChargeForCreate.hotelId(), roomChargeForCreate.roomType());

        roomChargeOutputPort.findById(
                RoomChargeForFind.of(roomChargeForCreate.hotelId(), roomChargeForCreate.roomType(), roomChargeForCreate.date())
        ).ifPresent(roomCharge -> {
            throw new RoomChargeDuplicatedException();
        });

        return roomChargeOutputPort.create(roomChargeForCreate);
    }

    public RoomCharge findOne(RoomChargeForFind roomChargeForFind) {
        log.debug("[RoomChargeService] [findOne] hotelId ::: {}, roomType ::: {}", roomChargeForFind.hotelId(), roomChargeForFind.roomType());

        return roomChargeOutputPort.findById(
                RoomChargeForFind.of(roomChargeForFind.hotelId(), roomChargeForFind.roomType(), roomChargeForFind.date())
        ).orElseThrow(RoomChargeNotFoundException::new);
    }

    @Transactional
    public RoomCharge update(RoomChargeForCreate roomChargeForCreate) {
        log.debug("[RoomChargeService] [update] hotelId ::: {}, roomType ::: {}", roomChargeForCreate.hotelId(), roomChargeForCreate.roomType());

        RoomCharge roomCharge = roomChargeOutputPort.findById(
                RoomChargeForFind.of(roomChargeForCreate.hotelId(), roomChargeForCreate.roomType(), roomChargeForCreate.date())
        ).orElseThrow(RoomChargeNotFoundException::new);

        roomCharge.updateCharge(roomChargeForCreate.charge());

        return roomCharge;
    }
}
