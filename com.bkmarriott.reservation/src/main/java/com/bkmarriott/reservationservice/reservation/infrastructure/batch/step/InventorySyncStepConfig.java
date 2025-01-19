package com.bkmarriott.reservationservice.reservation.infrastructure.batch.step;

import com.bkmarriott.reservationservice.reservation.infrastructure.batch.item.RoomKeyCheckResult;
import com.bkmarriott.reservationservice.reservation.infrastructure.persistence.entity.InventoryHistoryEntity;
import com.bkmarriott.reservationservice.reservation.infrastructure.persistence.repository.InventoryHistoryRepository;
import com.bkmarriott.reservationservice.reservation.infrastructure.persistence.repository.InventoryRepository;
import com.bkmarriott.reservationservice.reservation.infrastructure.persistence.util.RedisKeyParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class InventorySyncStepConfig {private final DataSource dataSource;

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final RedisTemplate<String, Long> redisTemplate;
    private final InventoryHistoryRepository inventoryHistoryRepository;
    private final InventoryRepository inventoryRepository;

    @Bean
    public Step validateAndSyncInventoryStep(){
        return new StepBuilder("syncInventory", jobRepository)
                .<String, RoomKeyCheckResult>chunk(10, transactionManager)
                .reader(distinctRoomKeyReader())
                .processor(historyReplayProcessor())
                .writer(inventorySyncWriter())
                .build();
    }

    @Bean
    @StepScope
    public JdbcCursorItemReader<String> distinctRoomKeyReader(){
        JdbcCursorItemReader<String> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(dataSource);
        reader.setSql("SELECT DISTINCT redis_room_key FROM m_inventory_history");
        reader.setRowMapper(((rs, rowNum) -> rs.getString("redis_room_key")));
        return reader;
    }

    @Bean
    public ItemProcessor<String, RoomKeyCheckResult> historyReplayProcessor(){
        return redisRoomKey ->{
            log.info("[BatchConfig] [historyReplayProcessor] redisRoomKey : {}", redisRoomKey);

            List<InventoryHistoryEntity> events = inventoryHistoryRepository.findByRedisRoomKeyOrderBySequenceNumberAsc(redisRoomKey);

            long redisInventoryStock = redisTemplate.opsForValue().get(redisRoomKey);

            if(events.isEmpty()){
                return new RoomKeyCheckResult(redisRoomKey, 0L, redisInventoryStock, false);
            }

            long calculated = events.get(0).getRoomStock();
            for (int i = 1; i < events.size(); i++) {
                InventoryHistoryEntity e = events.get(i);
                if ("PREPARED".equals(e.getEventType().toString())) {
                    calculated -= 1;
                } else if ("ROLLBACK".equals(e.getEventType().toString())) {
                    calculated += 1;
                }
            }

            boolean matched = (calculated == redisInventoryStock);

            return new RoomKeyCheckResult(redisRoomKey, calculated, redisInventoryStock, matched);
        };
    }

    @Bean
    public ItemWriter<RoomKeyCheckResult> inventorySyncWriter() {
        return items -> {
            for (RoomKeyCheckResult result : items) {
                if (!result.isMatched()) {
                    log.error("[BatchConfig] [inventorySyncWriter] MISMATCHED RedisRoomKey ::: {}, calculatedStock ::: {}, redisStock ::: {}", result.getRedisRoomKey(), result.getCalculatedStock(), result.getRedisStock());
                    // TODO: Kafka 전송 or 다른 처리

                } else {
                    log.info("[BatchConfig] [inventorySyncWriter] Matched RedisRoomKey ::: {}, calculatedStock ::: {}, redisStock ::: {}", result.getRedisRoomKey(), result.getCalculatedStock(), result.getRedisStock());

                    RedisKeyParser.ParsedKey parsedKey = RedisKeyParser.parseInventoryKey(result.getRedisRoomKey());
                    inventoryRepository.syncTotalReserved(parsedKey.hotelId(), parsedKey.date(), parsedKey.roomType(), (int) result.getCalculatedStock());
                }
            }
        };
    }
}
