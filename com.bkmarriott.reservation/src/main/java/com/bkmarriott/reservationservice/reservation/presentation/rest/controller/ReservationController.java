package com.bkmarriott.reservationservice.reservation.presentation.rest.controller;

import com.bkmarriott.reservationservice.reservation.application.service.ReservationService;
import com.bkmarriott.reservationservice.reservation.domain.Reservation;
import com.bkmarriott.reservationservice.reservation.presentation.rest.dto.auth.Actor;
import com.bkmarriott.reservationservice.reservation.presentation.rest.dto.command.ReservationModification;
import com.bkmarriott.reservationservice.reservation.presentation.rest.util.auth.LoginActor;
import com.bkmarriott.reservationservice.reservation.presentation.rest.util.reponse.ApiResponse;
import com.bkmarriott.reservationservice.reservation.presentation.rest.util.reponse.ApiResponse.Success;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<Success<ReservationModification.Response>> createReservation(
            @RequestBody ReservationModification.Request request,
            @LoginActor Actor actor
    ) {
        Reservation reservation = reservationService.createReservation(request.toDomain(actor.userId()));
        return ApiResponse.success(ReservationModification.Response.from(reservation), HttpStatus.CREATED);
    }
}
