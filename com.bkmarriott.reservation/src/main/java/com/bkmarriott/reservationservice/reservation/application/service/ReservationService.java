package com.bkmarriott.reservationservice.reservation.application.service;

import com.bkmarriott.reservationservice.reservation.application.exception.PaymentFailureException;
import com.bkmarriott.reservationservice.reservation.application.exception.RoomInventoryQuantityException;
import com.bkmarriott.reservationservice.reservation.application.outputport.CouponOutputPort;
import com.bkmarriott.reservationservice.reservation.application.outputport.PaymentOutputPort;
import com.bkmarriott.reservationservice.reservation.application.outputport.ReservationCommandOutputPort;
import com.bkmarriott.reservationservice.reservation.domain.Reservation;
import com.bkmarriott.reservationservice.reservation.domain.vo.ReservationForCreate;
import com.bkmarriott.reservationservice.reservation.infrastructure.feignClient.dto.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReservationService {

    private final InventoryService inventoryService;
    private final ReservationCommandOutputPort reservationCommandOutputPort;
    private final PaymentOutputPort paymentOutputPort;
    private final CouponOutputPort couponOutputPort;

    @Transactional
    public Reservation createReservation(ReservationForCreate domain) {
        // 인벤토리 확인
        boolean isNotQuantityEnough = inventoryService.getInventoryQuantity(domain.getHotelId(), domain.getStartDate(), domain.getEndDate())
                .stream()
                .anyMatch(inventoryQuantity -> !inventoryQuantity.isQuantityEnough(domain.getAmount()));
        if (isNotQuantityEnough) {
            throw new RoomInventoryQuantityException("예약 가능한 객실 수가 부족합니다.");
        }

        // 예약 PENDING 생성
        Reservation pendingReservation = reservationCommandOutputPort.createReservation(domain);

        // 결제 요청
        Payment payment = paymentOutputPort.getPayment(
                pendingReservation.getReservationId(),
                domain.getOriginalPrice(),
                domain.getFinalPrice(),
                domain.getAppliedCouponId()
        );
        if (payment.isFailed()) {
            throw new PaymentFailureException("결제가 실패하였습니다.");
        }

        // 인벤토리 차감
        inventoryService.updateTotalReserved(pendingReservation.getReservationId());

        // 쿠폰 사용
        boolean isSucceeded = couponOutputPort.useCoupon(domain.getAppliedCouponId());
        if (!isSucceeded) {
            throw new PaymentFailureException("쿠폰 사용에 실패하였습니다.");
        }

        // 예약 Status 업데이트
        return reservationCommandOutputPort.updateReservationStatus(pendingReservation, payment.getPaymentStatus().toReservationStatus());
    }
}
