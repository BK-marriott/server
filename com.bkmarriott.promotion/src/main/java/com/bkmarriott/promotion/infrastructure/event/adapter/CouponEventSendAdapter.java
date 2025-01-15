package com.bkmarriott.promotion.infrastructure.event.adapter;

import com.bkmarriott.promotion.application.outputport.CouponExternalMessageSender;
import com.bkmarriott.promotion.domain.event.CouponIssuanceEvent;
import com.bkmarriott.promotion.domain.event.DomainEventEnvelop;
import com.bkmarriott.promotion.infrastructure.event.publisher.CouponIssuanceEventPublisher;
import com.bkmarriott.promotion.infrastructure.persistence.entity.CouponIssuanceOutboxEntity;
import com.bkmarriott.promotion.infrastructure.persistence.repository.CouponIssuanceOutboxRepository;
import com.bkmarriott.promotion.infrastructure.persistence.util.EventConverter;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CouponEventSendAdapter implements CouponExternalMessageSender {

    private final CouponIssuanceEventPublisher eventPublisher;
    private final CouponIssuanceOutboxRepository couponIssuanceOutboxRepository;
    private final EventConverter eventConverter;

    @Transactional
    public void send(CouponIssuanceOutboxEntity entity) {
        entity = couponIssuanceOutboxRepository.findById(entity.getOutboxId()).orElseThrow(() ->
            new IllegalArgumentException("Outbox not found"));

        if (!entity.isPublished()) {
            DomainEventEnvelop<CouponIssuanceEvent> envelop = eventConverter.parseToEnvelopFrom(
                entity);
            eventPublisher.publish(envelop);
            entity.toPublished();
        }
    }

    @Override
    public DomainEventEnvelop<CouponIssuanceEvent> sendMessage(DomainEventEnvelop<CouponIssuanceEvent> message) {
        log.info("[CouponEventSendAdapter] [sendMessage] message ::: {}", message);
        eventPublisher.publish(message);
        return message;
    }
}
