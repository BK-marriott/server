package com.bkmarriott.reservationservice.reservation.infrastructure.persistence.entity;

import com.bkmarriott.reservationservice.reservation.domain.Reservation;
import com.bkmarriott.reservationservice.reservation.domain.vo.ReservationForCreate;
import com.bkmarriott.reservationservice.reservation.domain.vo.ReservationStatus;
import com.bkmarriott.reservationservice.reservation.domain.vo.RoomType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Table(name = "m_reservation")
@Entity
public class ReservationEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_id", nullable = false)
  private Long userId;

  @Column(name = "hotel_id", nullable = false)
  private Long hotelId;

  @Column(name = "room_id")
  private Long roomId;

  @Column(name = "start_date", nullable = false)
  private LocalDate startDate;

  @Column(name = "end_date", nullable = false)
  private LocalDate endDate;

  @Column(name = "room_type", nullable = false)
  @Enumerated(EnumType.STRING)
  private RoomEntityType roomType;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private ReservationEntityStatus status;

  public ReservationEntity(Long id, Long guestId, Long hotelId, Long roomId, LocalDate startDate, LocalDate endDate, RoomType roomType, ReservationStatus status) {
    this.id = id;
    this.userId = guestId;
    this.hotelId = hotelId;
    this.roomId = roomId;
    this.startDate = startDate;
    this.endDate = endDate;
    this.roomType = RoomEntityType.fromDomain(roomType);
    this.status = ReservationEntityStatus.fromDomain(status);
  }

  public Reservation toDomain() {
    return new Reservation(
        id,
        userId,
        hotelId,
        startDate,
        endDate,
        roomType.toDomain(),
        status.toDomain());
  }

  public static ReservationEntity fromDomain(Reservation reservation) {
    return new ReservationEntity(
        reservation.getReservationId(),
        reservation.getUserId(),
        reservation.getHotelId(),
        null,
        reservation.getStartDate(),
        reservation.getEndDate(),
        reservation.getRoomType(),
        reservation.getStatus());
  }

  public static ReservationEntity from(ReservationForCreate reservationForCreate) {
    return new ReservationEntity(
            null,
            reservationForCreate.getGuestId(),
            reservationForCreate.getHotelId(),
            null,
            reservationForCreate.getStartDate(),
            reservationForCreate.getEndDate(),
            reservationForCreate.getRoomType(),
            ReservationStatus.PENDING
    );
  }
}
