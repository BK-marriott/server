package com.bkmarriott.hotel.presentation.rest.dto.response;


import com.bkmarriott.hotel.domain.Hotel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public record HotelResponse(
        Long hotelId,
        String name,
        String city,
        String country,
        String address,
        String description,
        int charge
) {
    public static HotelResponse toDto(Hotel hotel) {
        return new HotelResponse(
                hotel.getHotelId(),
                hotel.getName(),
                hotel.getCity(),
                hotel.getCountry(),
                hotel.getAddress(),
                hotel.getDescription(),
                // TODO: OpenFeign을 사용하여 1박 단위 요금을 가져와서 적용
                10000
        );
    }
}
