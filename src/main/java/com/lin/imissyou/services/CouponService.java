package com.lin.imissyou.services;

import com.lin.imissyou.core.enumeration.CouponStatus;
import com.lin.imissyou.exception.http.NotFoundException;
import com.lin.imissyou.exception.http.ParameterException;
import com.lin.imissyou.model.Activity;
import com.lin.imissyou.model.Coupon;
import com.lin.imissyou.model.UserCoupon;
import com.lin.imissyou.repository.ActivityRepository;
import com.lin.imissyou.repository.CouponRepository;
import com.lin.imissyou.repository.UserCouponRepository;
import com.lin.imissyou.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CouponService {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UserCouponRepository userCouponRepository;

    public List<Coupon> getByCategory(Long cid) {
        Date now = new Date();
        return couponRepository.findByCategory(cid, now);
    }

    public List<Coupon> getWholeStoreCoupons() {
        Date now = new Date();
        return couponRepository.findByWholeStore(true, now);
    }

    public void collectOneCoupon(Long uid, Long couponId) {
        this.couponRepository.findById(couponId).orElseThrow(() -> new NotFoundException(40004));
        Activity activity = this.activityRepository.findByCouponListId(couponId).orElseThrow(() -> new NotFoundException(40010));
        Date now = new Date();
        Boolean isIn = CommonUtil.isInTimeLine(now, activity.getStartTime(), activity.getEndTime());
        if(!isIn){
            throw new ParameterException(40005);
        }
        this.userCouponRepository.findFirstByUserIdAndCouponId(uid,couponId).ifPresent((uc)-> {throw new ParameterException(40006);});
        UserCoupon userCoupon = UserCoupon.builder()
                .userId(uid)
                .couponId(couponId)
                .status(CouponStatus.AVAILABLE.getValue())
                .createTime(now)
                .build();
        this.userCouponRepository.save(userCoupon);
    }

}
