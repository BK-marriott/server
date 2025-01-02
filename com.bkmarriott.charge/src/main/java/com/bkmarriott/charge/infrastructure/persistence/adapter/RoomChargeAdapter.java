package com.bkmarriott.charge.infrastructure.persistence.adapter;

import com.bkmarriott.charge.application.outputport.RoomChargeOutputPort;
import com.bkmarriott.charge.domain.RoomCharge;
import com.bkmarriott.charge.domain.vo.RoomChargeForCreate;
import com.bkmarriott.charge.domain.vo.RoomChargeForFind;
import com.bkmarriott.charge.infrastructure.persistence.entity.RoomChargeEntity;
import com.bkmarriott.charge.infrastructure.persistence.entity.RoomChargeId;
import com.bkmarriott.charge.infrastructure.persistence.repository.RoomChargeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class RoomChargeAdapter implements RoomChargeOutputPort {

    private final RoomChargeRepository roomChargeRepository;

    public Optional<RoomCharge> findById(RoomChargeForFind roomChargeForFind) {
        return roomChargeRepository.findByIdAndIsDeletedFalse(RoomChargeId.fromDomain(roomChargeForFind))
                .map(RoomChargeEntity::toDomain);
    }

    public RoomCharge create(RoomChargeForCreate roomChargeForCreate) {
        RoomChargeEntity roomChargeEntity = RoomChargeEntity.from(roomChargeForCreate);
        return roomChargeRepository.save(roomChargeEntity).toDomain();
    }
}
