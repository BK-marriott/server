package com.bkmarriott.coupon.presentation.rest.dto.response;

import com.bkmarriott.coupon.domain.UserCoupon;
import java.util.List;

public record GetUserCouponListResponse(
        List<UserCoupon> userCouponList
) {
    public static GetUserCouponListResponse from(List<UserCoupon> userCoupons) {
        return new GetUserCouponListResponse(userCoupons);
    }
}
