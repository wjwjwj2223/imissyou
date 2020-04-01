package com.lin.imissyou.services;

import com.lin.imissyou.dto.OrderDTO;
import com.lin.imissyou.dto.SkuInfoDTO;
import com.lin.imissyou.exception.http.NotFoundException;
import com.lin.imissyou.exception.http.ParameterException;
import com.lin.imissyou.logic.CouponChecker;
import com.lin.imissyou.model.Coupon;
import com.lin.imissyou.model.UserCoupon;
import com.lin.imissyou.repository.CouponRepository;
import com.lin.imissyou.repository.UserCouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private SkuService skuService;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private UserCouponRepository userCouponRepository;

    @Autowired
    private CouponChecker couponChecker;

    public void isOk(Long uid, OrderDTO orderDTO) {

        if (orderDTO.getFinalTotalPrice().compareTo(new BigDecimal("0")) <=0 ) {
            throw new ParameterException(50011);
        }

        List<Long> skuIdList = orderDTO.getSkuInfoList()
                .stream()
                .map(SkuInfoDTO::getId)
                .collect(Collectors.toList());

        skuService.getSkuListByIds(skuIdList);

        Long couponId = orderDTO.getCouponId();
        if (couponId != null) {
            Coupon coupon = this.couponRepository.findById(couponId)
                    .orElseThrow(() -> new NotFoundException(40004));
            UserCoupon userCoupon = this.userCouponRepository.findFirstByUserIdAndCouponId(uid, couponId)
                    .orElseThrow(() -> new NotFoundException(50006));
            this.couponChecker.setCoupon(coupon);
            this.couponChecker.setUserCoupon(userCoupon);

        }
    }
}
