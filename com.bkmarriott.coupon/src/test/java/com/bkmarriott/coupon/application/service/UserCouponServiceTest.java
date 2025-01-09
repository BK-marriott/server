package com.bkmarriott.coupon.application.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.bkmarriott.coupon.application.outputport.UserCouponOutputPort;
import com.bkmarriott.coupon.domain.Coupon;
import com.bkmarriott.coupon.domain.UserCoupon;
import com.bkmarriott.coupon.infrastructure.persistence.exception.CouponNotSpentException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("[Application] [Unit] UserCoupon Service Test")
@ExtendWith(MockitoExtension.class)
public class UserCouponServiceTest {

    @InjectMocks private UserCouponService userCouponService;
    @Mock private UserCouponOutputPort userCouponOutputPort;
    private final Long userCouponId = 1L;

    private UserCoupon generateTestUserCoupon(LocalDateTime spentAt) {
        return new UserCoupon(
                userCouponId,
                new Coupon(1L, null, "Test Coupon", 0.1f),
                1234L,
                LocalDateTime.of(2025, 1, 4, 0, 0, 0),
                spentAt,
                LocalDateTime.of(2025, 1, 31, 0, 0, 0),
                1L
        );
    }

    @Test
    @DisplayName("[성공] 사용자 쿠폰 사용 롤백 테스트 - 이미 사용한 쿠폰을 미사용 쿠폰으로 롤백")
    void cancelUserCoupon_successTest() {
        // Given : 사용 쿠폰
        UserCoupon testUserCoupon = generateTestUserCoupon(LocalDateTime.of(2025, 1, 9, 0, 0, 0));
        when(userCouponOutputPort.findById(userCouponId)).thenReturn(testUserCoupon);
        when(userCouponOutputPort.cancelUserCouponUsage(any())).thenAnswer(invocation -> {
            UserCoupon userCoupon = invocation.getArgument(0);
            userCoupon.deleteSpentAt();
            return userCoupon;
        });

        // When
        UserCoupon actual = userCouponService.cancelUserCouponUsage(userCouponId);

        // When & Then
        Assertions.assertAll(
                () -> Assertions.assertNull(actual.getSpentAt())
        );
    }

    @Test
    @DisplayName("[실패] 사용자 쿠폰 사용 롤백 테스트 - 아직 사용 전인 쿠폰은 롤백 불가")
    void cancelUserCoupon_failureTest() {
        // Given : 미사용 쿠폰
        UserCoupon testUserCoupon = generateTestUserCoupon(null);

        when(userCouponOutputPort.findById(userCouponId)).thenReturn(testUserCoupon);

        // When & Then
        Assertions.assertThrows(
                CouponNotSpentException.class,
                () -> userCouponService.cancelUserCouponUsage(userCouponId)
        );
    }
}
