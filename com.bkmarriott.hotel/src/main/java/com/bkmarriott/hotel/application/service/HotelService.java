package com.bkmarriott.hotel.application.service;

import com.bkmarriott.hotel.domain.Hotel;
import com.bkmarriott.hotel.infrastructure.persistence.adapter.HotelQueryAdapter;
import com.bkmarriott.hotel.presentation.rest.dto.request.HotelSearchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelQueryAdapter hotelQueryAdapter;

    public Page<Hotel> searchHotel(HotelSearchRequest searchRequest, Pageable pageable) {

        // TODO 1박 단위 요금 받아오기

        return hotelQueryAdapter.searchHotel(searchRequest, pageable);
    }
}
