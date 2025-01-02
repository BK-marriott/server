package com.bkmarriott.charge.infrastructure.persistence.adapter;

import com.bkmarriott.charge.domain.RoomCharge;
import com.bkmarriott.charge.domain.vo.RoomChargeForCreate;
import com.bkmarriott.charge.domain.vo.RoomType;
import com.bkmarriott.charge.infrastructure.persistence.config.RepositoryTest;
import com.bkmarriott.charge.infrastructure.persistence.entity.RoomChargeId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Optional;

@DisplayName("[Infrastructure] RoomCharge Repository Unit Test")
@RepositoryTest
class RoomChargeCommandAdapterTest {

    @Autowired
    private RoomChargeCommandAdapter roomChargeCommandAdapter;

    @Test
    @DisplayName("[객실 요금 등록 성공 테스트] 객실 요금을 등록한 뒤 도메인 객체를 반환한다.")
    void createRoomCharge_successTest() {
        // Given
        RoomChargeForCreate roomChargeForCreate = genRoomChargeForCreate();
        // When
        RoomCharge actual = roomChargeCommandAdapter.create(roomChargeForCreate);
        // Then
        Assertions.assertAll(
                () -> Assertions.assertEquals(roomChargeForCreate.hotelId(), actual.getHotelId()),
                () -> Assertions.assertEquals(roomChargeForCreate.roomType(), actual.getRoomType()),
                () -> Assertions.assertEquals(roomChargeForCreate.charge(), actual.getCharge()),
                () -> Assertions.assertEquals(roomChargeForCreate.date(), actual.getDate())
        );
    }

    private RoomChargeForCreate genRoomChargeForCreate() {
        Long hotelId = 1L;
        RoomType roomType = RoomType.STANDARD;
        Integer charge = 10000;
        LocalDate date = LocalDate.now();

        return RoomChargeForCreate.of(hotelId, roomType, charge, date);
    }

    @Test
    @DisplayName("[객실 요금 조회 성공 테스트] 호텔 아이디, 객실 타입으로 객실 요금을 조회한 뒤 Optional 객체에 담아 반환한다.")
    void findById_successTest() {
        // Given
        RoomChargeForCreate roomChargeForCreate = genRoomChargeForCreate();
        RoomCharge roomCharge = roomChargeCommandAdapter.create(roomChargeForCreate);
        // When
        Optional<RoomCharge> optionalRoomCharge = roomChargeCommandAdapter.findById(RoomChargeId.from(roomCharge.getHotelId(), roomCharge.getRoomType()));
        // Then
        Assertions.assertAll(
                () -> Assertions.assertTrue(optionalRoomCharge.isPresent()),
                () -> Assertions.assertEquals(roomCharge.getHotelId(), optionalRoomCharge.get().getHotelId()),
                () -> Assertions.assertEquals(roomCharge.getRoomType(), optionalRoomCharge.get().getRoomType())
        );
    }
}