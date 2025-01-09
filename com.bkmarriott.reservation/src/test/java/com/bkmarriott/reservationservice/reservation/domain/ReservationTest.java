package com.bkmarriott.reservationservice.reservation.domain;

import com.bkmarriott.reservationservice.reservation.domain.vo.ReservationStatus;
import com.bkmarriott.reservationservice.reservation.domain.vo.RoomType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

@DisplayName("[Domain] Reservation Unit test")
class ReservationTest {

    @Test
    void updateStatus_successTest() {
        // Given
        Reservation reservation = new Reservation(
                1L,
                111L,
                12L,
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 1, 2),
                RoomType.STANDARD,
                ReservationStatus.PENDING
        );

        // When
        Reservation updatedReservation = reservation.updateStatus(ReservationStatus.PAID);

        // Then
        Assertions.assertEquals(reservation.getReservationId(), updatedReservation.getReservationId());
        Assertions.assertEquals(ReservationStatus.PAID, updatedReservation.getStatus());
    }
}