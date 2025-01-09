package com.bkmarriott.reservationservice.reservation.application.service;

import com.bkmarriott.reservationservice.reservation.application.outputport.CouponOutputPort;
import com.bkmarriott.reservationservice.reservation.application.outputport.PaymentOutputPort;
import com.bkmarriott.reservationservice.reservation.domain.vo.ReservationForCreate;
import com.bkmarriott.reservationservice.reservation.domain.vo.RoomType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
@DisplayName("[Application] ReservationService Unit test")
class ReservationServiceTest {

    @InjectMocks
    private ReservationService ReservationService;
    @Mock
    private InventoryService inventoryService;
    @Mock
    private PaymentOutputPort paymentOutputPort;
    @Mock
    private CouponOutputPort couponOutputPort;

    @Test
    @DisplayName("[예약 생성 성공 테스트] 예약 생성 후 예약 정보를 반환한다.")
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

//        Mockito.when(inventoryService.getInventoryQuantity(
//                ArgumentMatchers.anyLong(),
//                ArgumentMatchers.any(LocalDate.class),
//                ArgumentMatchers.any(LocalDate.class)
//        )).thenReturn()
    }
}