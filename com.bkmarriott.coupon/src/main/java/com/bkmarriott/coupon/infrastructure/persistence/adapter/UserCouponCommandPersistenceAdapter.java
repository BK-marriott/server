package com.bkmarriott.coupon.infrastructure.persistence.adapter;

import com.bkmarriott.coupon.application.outputport.UserCouponOutputPort;
import com.bkmarriott.coupon.domain.UserCoupon;
import com.bkmarriott.coupon.infrastructure.persistence.entity.UserCouponEntity;
import com.bkmarriott.coupon.infrastructure.persistence.repository.UserCouponRepository;
import com.bkmarriott.coupon.presentation.rest.exception.UserCouponNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class UserCouponCommandPersistenceAdapter implements UserCouponOutputPort {

    private final UserCouponRepository userCouponRepository;

    @Override
    @Transactional
    public UserCoupon generateUserCoupon(UserCoupon userCoupon) {
        UserCouponEntity userCouponEntity = UserCouponEntity.from(userCoupon);
        userCouponEntity = userCouponRepository.save(userCouponEntity);

        return userCouponEntity.toDomain();
    }

    @Override
    public UserCoupon findValidCouponById(Long userCouponId) {
        UserCouponEntity userCouponEntity =
                userCouponRepository.findValidCouponById(userCouponId, LocalDateTime.now())
                        .orElseThrow(UserCouponNotFoundException::new);

        return userCouponEntity.toDomain();
    }

    @Override
    @Transactional
    public UserCoupon update(UserCoupon userCoupon) {
        UserCouponEntity userCouponEntity = userCouponRepository.findValidCouponById(userCoupon.getId(), LocalDateTime.now())
                .orElseThrow(UserCouponNotFoundException::new);

        userCouponEntity = userCouponEntity.updateSpentAt(userCoupon);
        userCouponRepository.save(userCouponEntity);

        return userCouponEntity.toDomain();
    }
}
