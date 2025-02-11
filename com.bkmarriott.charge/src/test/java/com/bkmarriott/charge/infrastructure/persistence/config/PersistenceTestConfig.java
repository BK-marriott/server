package com.bkmarriott.charge.infrastructure.persistence.config;

import com.bkmarriott.charge.infrastructure.persistence.adapter.HotelTypeAdapter;
import com.bkmarriott.charge.infrastructure.persistence.adapter.RoomChargeAdapter;
import com.bkmarriott.charge.infrastructure.persistence.repository.DefaultRoomChargeRepository;
import com.bkmarriott.charge.infrastructure.persistence.repository.HotelTypeRepository;
import com.bkmarriott.charge.infrastructure.persistence.repository.RoomChargeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class PersistenceTestConfig {

    @Bean
    public RoomChargeAdapter roomChargeAdapter(
            @Autowired RoomChargeRepository roomChargeRepository,
            @Autowired DefaultRoomChargeRepository defaultRoomChargeRepository,
            @Autowired JdbcTemplate jdbcTemplate
    ) {
        return new RoomChargeAdapter(roomChargeRepository, defaultRoomChargeRepository, jdbcTemplate);
    }

    @Bean
    public HotelTypeAdapter hotelTypeAdapter(
            @Autowired HotelTypeRepository roomChargeRepository
    ) {
        return new HotelTypeAdapter(roomChargeRepository);
    }
}
