package com.bkmarriott.hotel.application.service;

import com.bkmarriott.hotel.domain.Hotel;
import com.bkmarriott.hotel.infrastructure.persistence.adapter.HotelQueryAdapter;
import com.bkmarriott.hotel.presentation.rest.dto.request.HotelSearchRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@DisplayName("[Application] HotelService Unit Test")
public class HotelServiceTest {

    @InjectMocks private HotelService hotelService;
    @Mock private HotelQueryAdapter hotelQueryAdapter;

    @Test
    @DisplayName("[호텔 검색 성공 테스트] 요청받은 조건에 맞는 호텔 정보를 반환한다.")
    public void searchHotel_success() throws Exception {
        // Given
        HotelSearchRequest request = new HotelSearchRequest("Marriott", "Seoul", LocalDate.parse("2025-01-02"), LocalDate.parse("2025-01-03"));
        Pageable pageable = PageRequest.of(0, 10);
        Page<Hotel> mockResponse = new PageImpl<>(List.of(
                new Hotel(1L,"Marriott Hotel Seoul","South Korea", "Seoul", "address", "description")
        ));

        Mockito.when(hotelQueryAdapter.searchHotel(request, pageable)).thenReturn(mockResponse);

        // When
        Page<Hotel> result = hotelService.searchHotel(request, pageable);

        //then
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("Marriott Hotel Seoul", result.getContent().get(0).getName());
        Mockito.verify(hotelQueryAdapter, times(1)).searchHotel(request, pageable);

    }

}
