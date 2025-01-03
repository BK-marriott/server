package com.bkmarriott.reservationservice.reservation.application.service;

import com.bkmarriott.reservationservice.reservation.application.exception.InventoryUpdateFailureException;
import com.bkmarriott.reservationservice.reservation.application.outputport.InventoryCommandOutputPort;
import com.bkmarriott.reservationservice.reservation.domain.Inventory;
import com.bkmarriott.reservationservice.reservation.domain.Reservation;
import com.bkmarriott.reservationservice.reservation.domain.vo.ReservationStatus;
import com.bkmarriott.reservationservice.reservation.infrastructure.persistence.adapter.ReservationQueryAdaptor;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryService {

  private final InventoryCommandOutputPort inventoryCommandOutputPort;
  private final ReservationQueryAdaptor reservationQueryAdaptor;

  public List<Inventory> updateTotalReserved(Long hotelId) {

    Reservation reservation = reservationQueryAdaptor.findById(hotelId).toDomain();

    try {

      if (reservation.getStatus().equals(ReservationStatus.PAID)) {
          log.debug("[InventoryService] [Increase totalReserved] hotelId ::: {}, roomtype ::: {}"
              , reservation.getHotelId(), reservation.getRoomType());

        return reservation.toDamain().stream()
            .map(inventoryCommandOutputPort::increaseReserved)
            .toList();
        }

      if (reservation.getStatus().equals(ReservationStatus.CANCELLED) || reservation.getStatus().equals(ReservationStatus.REFUNDED)) {
        log.debug("[InventoryService] [Decrease totalReserved] hotelId ::: {}, roomtype ::: {}"
            , reservation.getHotelId(), reservation.getRoomType());

        return reservation.toDamain().stream()
            .map(inventoryCommandOutputPort::decreaseReserved)
            .toList();
        }

      throw new IllegalArgumentException("잘못된 예약 상태 정보");

    } catch (Exception e) {
      log.error("[InventoryService] [updateTotalReserved] hotelId ::: {}, roomType ::: {}",
          reservation.getHotelId(), reservation.getRoomType(), e);
      throw new InventoryUpdateFailureException("객실 예약 인벤토리 정보 수정 실패");
    }

  }
}
