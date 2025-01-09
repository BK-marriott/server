package com.bkmarriott.reservationservice.reservation.infrastructure.persistence.adapter;

import com.bkmarriott.reservationservice.reservation.application.outputport.ReservationCommandOutputPort;
import com.bkmarriott.reservationservice.reservation.domain.Reservation;
import com.bkmarriott.reservationservice.reservation.domain.vo.ReservationForCreate;
import com.bkmarriott.reservationservice.reservation.domain.vo.ReservationStatus;
import com.bkmarriott.reservationservice.reservation.infrastructure.persistence.entity.ReservationEntity;
import com.bkmarriott.reservationservice.reservation.infrastructure.persistence.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class ReservationCommandAdaptor implements ReservationCommandOutputPort {

    private final ReservationRepository reservationRepository;

    @Override
    public Reservation createReservation(ReservationForCreate reservationForCreate) {
        return reservationRepository.save(ReservationEntity.from(reservationForCreate)).toDomain();
    }

    @Override
    public Reservation updateReservationStatus(Reservation reservation, ReservationStatus status) {
        reservation.updateStatus(status);
        return reservationRepository.save(ReservationEntity.fromDomain(reservation)).toDomain();
    }
}
