package com.bkmarriott.promotion.infrastructure.schedule.scheduler;

import static org.junit.jupiter.api.Assertions.*;

import com.bkmarriott.promotion.application.schedule.sceduler.CouponIssuancePublishScheduler;
import com.bkmarriott.promotion.infrastructure.event.publisher.CouponIssuanceEventPublisher;
import com.bkmarriott.promotion.infrastructure.persistence.adapter.CouponEventPersistenceAdapter;
import com.bkmarriott.promotion.infrastructure.persistence.util.EventConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("[Infrastructure] [Unit] CouponIssuancePublishScheduler Test")
@ExtendWith(MockitoExtension.class)
class CouponIssuancePublishSchedulerTest {

    @InjectMocks
    private CouponIssuancePublishScheduler couponIssuancePublishScheduler;

    @Mock private CouponEventPersistenceAdapter couponEventPersistenceAdapter;
    @Mock private CouponIssuanceEventPublisher couponIssuanceEventPublisher;
    @Mock private EventConverter eventConverter;

//    @Test
//    @DisplayName("[성공] 쿠폰 발급 스케줄링 테스트 - 발급되지 않은 오래된 이벤트가 있는 경우 이벤트를 발급한다.")
//    public void scheduleCouponIssuancePublish_successTest_publishOldEvent() {
//        // Given
//        // When
//        // Then
//        Assertions.assertAll(
//            () -> Assertions.assertEquals()
//        );
//    }
}