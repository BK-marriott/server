package com.bkmarriott.hotel.presentation.rest.controller;

import com.bkmarriott.hotel.application.service.HotelService;
import com.bkmarriott.hotel.presentation.rest.dto.request.HotelSearchRequest;
import com.bkmarriott.hotel.presentation.rest.dto.response.HotelResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hotels")
public class HotelController {

    private final HotelService hotelService;

    @GetMapping
    public ResponseEntity<Page<HotelResponse>> searchHotel(@Valid HotelSearchRequest searchRequest, @PageableDefault Pageable pageable){

        return ResponseEntity.ok().body(hotelService.searchHotel(searchRequest, pageable).map(HotelResponse::toDto));
    }
}
