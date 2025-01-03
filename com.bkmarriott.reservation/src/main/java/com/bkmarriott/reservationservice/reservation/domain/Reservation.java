package com.bkmarriott.reservationservice.reservation.domain;

import com.bkmarriott.reservationservice.reservation.domain.vo.ReservationStatus;
import com.bkmarriott.reservationservice.reservation.domain.vo.RoomType;
import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Reservation {

  private Long reservationId;
  private Long userId;
  private Long hotelId;
  private LocalDate startDate;
  private LocalDate endDate;
  private RoomType roomType;
  private ReservationStatus status;

  public List<Inventory> toDamain() {
    return getDateRange(this.startDate, this.endDate).stream()
        .map(date -> Inventory.builder()
            .hotelId(hotelId)
            .date(date)
            .roomType(roomType)
            .build())
        .toList();
  }

  public static List<LocalDate> getDateRange(LocalDate startDate, LocalDate endDate) {

    return startDate.datesUntil(endDate).toList(); // endDate 제외
  }
}
