package com.bkmarriott.reservationservice.reservation.infrastructure.persistence.adapter;

import com.bkmarriott.reservationservice.reservation.domain.Reservation;
import com.bkmarriott.reservationservice.reservation.domain.vo.ReservationForCreate;
import com.bkmarriott.reservationservice.reservation.domain.vo.ReservationStatus;
import com.bkmarriott.reservationservice.reservation.domain.vo.RoomType;
import com.bkmarriott.reservationservice.reservation.infrastructure.persistence.config.RepositoryTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

@DisplayName("[Infrastructure] ReservationCommandAdapter Unit test")
@RepositoryTest
class ReservationCommandAdaptorTest {

    @Autowired
    private ReservationCommandAdaptor reservationCommandAdaptor;

    @Test
    @DisplayName("[예약 생성 성공 테스트] 예약을 생성한 뒤 도메인 객체를 반환한다.")
    void createReservation_successTest() {
        // Given
        ReservationForCreate reservationForCreate = new ReservationForCreate(
                1L,
                11L,
                RoomType.STANDARD,
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 1, 2),
                2,
                100000,
                10000,
                123L
        );

        // When
        Reservation actual = reservationCommandAdaptor.createReservation(reservationForCreate);

        // Then
        Assertions.assertAll(
                () -> Assertions.assertEquals(reservationForCreate.getHotelId(), actual.getHotelId()),
                () -> Assertions.assertEquals(reservationForCreate.getRoomType(), actual.getRoomType()),
                () -> Assertions.assertEquals(reservationForCreate.getGuestId(), actual.getUserId())
        );
    }

    @Test
    @DisplayName("[예약 상태 수정 성공 테스트] 예약의 상태를 수정 후 도메인 객체를 반환한다.")
    void updateReservationStatus() {
        // Given
        Reservation reservation = new Reservation(
                1L,
                11L,
                111L,
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 1, 2),
                RoomType.STANDARD,
                ReservationStatus.PENDING
        );
        ReservationStatus updateStatus = ReservationStatus.PAID;

        // When
        Reservation actual = reservationCommandAdaptor.updateReservationStatus(reservation, updateStatus);

        // Then
        Assertions.assertAll(
                () -> Assertions.assertEquals(reservation.getReservationId(), actual.getReservationId()),
                () -> Assertions.assertEquals(reservation.getHotelId(), actual.getHotelId()),
                () -> Assertions.assertEquals(reservation.getRoomType(), actual.getRoomType()),
                () -> Assertions.assertEquals(reservation.getStatus(), actual.getStatus())
        );
    }
}