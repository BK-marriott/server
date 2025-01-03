package com.bkmarriott.coupon.infrastructure.persistence.adapter;

import com.bkmarriott.coupon.application.outputport.MemberCouponOutputPort;
import com.bkmarriott.coupon.domain.MemberCoupon;
import com.bkmarriott.coupon.infrastructure.persistence.entity.MemberCouponEntity;
import com.bkmarriott.coupon.infrastructure.persistence.repository.MemberCouponRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberCouponQueryAdapter implements MemberCouponOutputPort {
    private final MemberCouponRepository memberCouponRepository;

    public MemberCoupon getById(Long id) {
        // TODO: CustomException 수정 필요
        return memberCouponRepository.findValidCouponById(id, LocalDateTime.now())
                .map(MemberCouponEntity::toDomain)
                .orElseThrow(RuntimeException::new);
    }
}
