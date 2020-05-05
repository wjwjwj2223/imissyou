
package com.lin.imissyou.api.v1;

import com.lin.imissyou.core.LocalUser;
import com.lin.imissyou.core.interceptors.ScopeLevel;
import com.lin.imissyou.exception.http.NotFoundException;
import com.lin.imissyou.model.Activity;
import com.lin.imissyou.model.UserCoupon;
import com.lin.imissyou.services.ActivityService;
import com.lin.imissyou.services.CouponService;
import com.lin.imissyou.vo.ActivityCouponVO;
import com.lin.imissyou.vo.ActivityPureVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("activity")
@RestController
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private CouponService couponService;

    @GetMapping("/name/{name}")
    public ActivityPureVO getHomeActivity(@PathVariable String name) {
        Activity activity = activityService.getByName(name);
        if (activity == null) {
            throw new NotFoundException(40001);
        }
        ActivityPureVO vo = new ActivityPureVO(activity);
        return vo;
    }

    @GetMapping("/name/{name}/with_coupon")
    @ScopeLevel
    public ActivityCouponVO getActivityWithCoupons(@PathVariable String name) {
        Long uid = LocalUser.getUser().getId();
        Activity activity = activityService.getByName(name);
        List<UserCoupon> userCoupons = couponService.getCollectedCoupons(uid);
        if (activity == null) {
            throw new NotFoundException(40001);
        }
        return new ActivityCouponVO(activity, userCoupons);
    }

}
