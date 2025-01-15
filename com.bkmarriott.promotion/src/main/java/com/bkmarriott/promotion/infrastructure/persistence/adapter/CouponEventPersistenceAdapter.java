package com.bkmarriott.promotion.infrastructure.persistence.adapter;

import com.bkmarriott.promotion.application.outputport.CouponExternalEventReader;
import com.bkmarriott.promotion.application.outputport.CouponExternalEventRecorder;
import com.bkmarriott.promotion.domain.event.CouponIssuanceEvent;
import com.bkmarriott.promotion.domain.event.DomainEventEnvelop;
import com.bkmarriott.promotion.infrastructure.persistence.entity.CouponIssuanceOutboxEntity;
import com.bkmarriott.promotion.infrastructure.persistence.repository.CouponIssuanceOutboxRepository;
import com.bkmarriott.promotion.infrastructure.persistence.util.EventConverter;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Component
public class CouponEventPersistenceAdapter implements
    CouponExternalEventRecorder, CouponExternalEventReader {

    private final CouponIssuanceOutboxRepository outboxRepository;
    private final EventConverter eventConverter;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public DomainEventEnvelop<CouponIssuanceEvent> record(CouponIssuanceEvent couponIssuanceEvent) {
        log.debug(
            "[CouponEventPersistenceAdapter] [issueCoupon] promotionId ::: {}, couponId ::: {}, userId ::: {}",
            couponIssuanceEvent.getPromotionId(), couponIssuanceEvent.getCouponId(), couponIssuanceEvent.getUserId()
        );
        CouponIssuanceOutboxEntity outboxEntity = parseFromEvent(couponIssuanceEvent);
        outboxEntity = outboxRepository.save(outboxEntity);

        eventPublisher.publishEvent(outboxEntity);
        return outboxEntity.toEnvelop(eventConverter);
    }

    private CouponIssuanceOutboxEntity parseFromEvent(CouponIssuanceEvent event) {
        DomainEventEnvelop<CouponIssuanceEvent> envelop = DomainEventEnvelop.of(
            event, "[promotion service] [CouponEventPersistenceAdapter] [issueCoupon]"
        );
        String eventJson = eventConverter.convertToJson(event);
        return CouponIssuanceOutboxEntity.from(envelop, eventJson);
    }

    @Override
    @Transactional
    public DomainEventEnvelop<CouponIssuanceEvent> record(DomainEventEnvelop<CouponIssuanceEvent> envelop) {
        String eventJson = eventConverter.convertToJson(envelop.getEvent());
        CouponIssuanceOutboxEntity entity = CouponIssuanceOutboxEntity.from(envelop, eventJson);
        entity = outboxRepository.save(entity);

        return entity.toEnvelop(eventConverter);
    }

    public List<CouponIssuanceOutboxEntity> findBeforePublished() {
        return outboxRepository.findAllByIsPublishedIsFalse();
    }

    @Override
    @Transactional
    public void recordToPublished(UUID recordId) {
        String id = String.valueOf(recordId);
        outboxRepository.findById(id).ifPresent(CouponIssuanceOutboxEntity::toPublished);
    }

    @Override
    public List<DomainEventEnvelop<CouponIssuanceEvent>> readAllBeforePublished() {
        return outboxRepository.findAllByIsPublishedIsFalse().stream()
            .map(outbox -> outbox.toEnvelop(eventConverter))
            .toList();
    }
}
