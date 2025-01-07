package com.bkmarriott.coupon.application.outputport;

import com.bkmarriott.coupon.domain.UserCoupon;

public interface UserCouponOutputPort {
    UserCoupon getById(Long id);
}
