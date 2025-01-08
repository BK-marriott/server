package com.bkmarriott.coupon.application.service;

import com.bkmarriott.coupon.application.exception.EventDuplicateException;
import com.bkmarriott.coupon.application.outputport.CouponEventLogOutputPort;
import com.bkmarriott.coupon.domain.UserCoupon;
import com.bkmarriott.coupon.domain.event.CouponIssuanceEvent;
import com.bkmarriott.coupon.domain.event.DomainEventEnvelop;
import com.bkmarriott.coupon.domain.vo.UserCouponForIssue;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CouponEventService {

    private final CouponEventLogOutputPort couponEventLogOutputPort;
    private final UserCouponService userCouponService;

    public UserCoupon issueCoupon(DomainEventEnvelop<CouponIssuanceEvent> envelop) {
        checkDuplicationEvent(envelop.getEventId());
        UserCouponForIssue userCouponForIssue = envelop.getEvent().toUserCouponForIssue();

        // 이벤트 처리 로그 저장
        UserCoupon userCoupon = userCouponService.issueCoupon(userCouponForIssue);
        this.logEvent(envelop);
        return userCoupon;
    }

    private void checkDuplicationEvent(UUID eventId) {
        String eventLogId = String.valueOf(eventId);
        boolean isDuplicated = couponEventLogOutputPort.isExistedCouponLog(eventLogId);
        if (isDuplicated) {
            throw new EventDuplicateException();
        }
    }

    private void logEvent(DomainEventEnvelop<CouponIssuanceEvent> envelop) {
        String eventId = String.valueOf(envelop.getEventId());
        couponEventLogOutputPort.generateCouponLog(eventId);
    }
}
