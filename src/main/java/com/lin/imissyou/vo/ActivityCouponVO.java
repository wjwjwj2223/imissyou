
package com.lin.imissyou.vo;

import com.lin.imissyou.model.Activity;
import com.lin.imissyou.model.UserCoupon;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class ActivityCouponVO extends ActivityPureVO {
    private List<CouponPureVO> coupons;

    public ActivityCouponVO(Activity activity, List<UserCoupon> userCoupons) {
        super(activity);
        Set<Long> couponIds = new HashSet<>();
        userCoupons.forEach(userCoupon -> {
            couponIds.add(userCoupon.getCouponId());
        });
        coupons = activity.getCouponList()
                .stream().map(CouponPureVO::new)
                .map(couponPureVO -> {
                    couponPureVO.setIsCollected(couponIds.contains(couponPureVO.getId()));
                    return couponPureVO;
                }).collect(Collectors.toList());
    }
}
