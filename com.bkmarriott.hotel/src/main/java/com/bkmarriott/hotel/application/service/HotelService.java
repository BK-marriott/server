package com.bkmarriott.hotel.application.service;

import com.bkmarriott.hotel.application.outputport.HotelQueryOutputPort;
import com.bkmarriott.hotel.domain.Hotel;
import com.bkmarriott.hotel.presentation.rest.dto.request.HotelSearchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelQueryOutputPort hotelQueryOutputPort;

    public Page<Hotel> searchHotel(HotelSearchRequest searchRequest, Pageable pageable) {

        // TODO 1박 단위 요금 받아오기

        return hotelQueryOutputPort.searchHotel(searchRequest, pageable);
    }
}
