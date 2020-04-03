package com.lin.imissyou.services;

import com.lin.imissyou.core.LocalUser;
import com.lin.imissyou.core.enumeration.OrderStatus;
import com.lin.imissyou.dto.OrderDTO;
import com.lin.imissyou.dto.SkuInfoDTO;
import com.lin.imissyou.exception.http.ForbiddenException;
import com.lin.imissyou.exception.http.NotFoundException;
import com.lin.imissyou.exception.http.ParameterException;
import com.lin.imissyou.logic.CouponChecker;
import com.lin.imissyou.logic.OrderChecker;
import com.lin.imissyou.model.*;
import com.lin.imissyou.repository.CouponRepository;
import com.lin.imissyou.repository.OrderRepository;
import com.lin.imissyou.repository.SkuRepository;
import com.lin.imissyou.repository.UserCouponRepository;
import com.lin.imissyou.util.CommonUtil;
import com.lin.imissyou.util.OrderUtil;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
    private ObjectFactory<CouponChecker> checkerObjectFactory;

    @Autowired
    private ObjectFactory<OrderChecker> orderCheckerObjectFactory;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SkuRepository skuRepository;

    @Value("${missyou.order.pay-time-limmit}")
    private int payTimeLimit;

    //校验
    public OrderChecker isOk(Long uid, OrderDTO orderDTO) {

        if (orderDTO.getFinalTotalPrice().compareTo(new BigDecimal("0")) <=0 ) {
            throw new ParameterException(50011);
        }

        List<Long> skuIdList = orderDTO.getSkuInfoList()
                .stream()
                .map(SkuInfoDTO::getId)
                .collect(Collectors.toList());

        List<Sku> skuList = skuService.getSkuListByIds(skuIdList);

        Long couponId = orderDTO.getCouponId();

        CouponChecker couponChecker = null;

        if (couponId != null) {
            Coupon coupon = this.couponRepository.findById(couponId)
                    .orElseThrow(() -> new NotFoundException(40004));
            UserCoupon userCoupon = this.userCouponRepository.findFirstByUserIdAndCouponIdAndStatus(uid, couponId, 1)
                    .orElseThrow(() -> new NotFoundException(50006));
            couponChecker = this.checkerObjectFactory.getObject();
            couponChecker.setCoupon(coupon);
        }

        OrderChecker orderChecker = this.orderCheckerObjectFactory.getObject();
        orderChecker.setCouponChecker(couponChecker);
        orderChecker.setOrderDTO(orderDTO);
        orderChecker.setServerSkuList(skuList);

        orderChecker.isOk();
        return orderChecker;
    }


    @Transactional
    public Long placeOrder(Long uid, OrderDTO orderDTO, OrderChecker orderChecker) {
        String orderNo = OrderUtil.makeOrderNo();
        Calendar now = Calendar.getInstance();
        Calendar now1 = (Calendar) now.clone();
        Date expired_time = CommonUtil.addSomeSeconods(now,this.payTimeLimit).getTime();
        Order order = Order.builder()
                .orderNo(orderNo)
                .totalPrice(orderDTO.getTotalPrice())
                .finalTotalPrice(orderDTO.getFinalTotalPrice())
                .userId(uid)
                .totalCount(orderChecker.getTotalCount().longValue())
                .snapImg(orderChecker.getLeaderImg())
                .snapTitle(orderChecker.getLeaderTitle())
                .status(OrderStatus.UNPAID.getValue())
                .expiredTime(expired_time)
                .placedTime(now1.getTime())
                .build();
        order.setSnapAddress(orderDTO.getAddress());
        order.setSnapItems(orderChecker.getOrderSkuList());
        this.orderRepository.save(order);
        //减库存
        this.reduceStock(orderChecker);
        //核销优惠券
        if (orderDTO.getCouponId() != null) {
            this.writeOffCoupon(orderDTO.getCouponId(), order.getId(), uid);
        }
        //加入到延迟消息队列
        return order.getId();
    }

    public Page<Order> getUnpaid(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page,size, Sort.by("createTime").descending());
        Long uid = LocalUser.getUser().getId();
        Date now = new Date();
        return this.orderRepository.findByExpiredTimeGreaterThanAndStatusAndUserId(now,OrderStatus.UNPAID.getValue(),uid,pageable);
    }

    private void reduceStock(OrderChecker orderChecker) {
        List<OrderSku> orderSkuList = orderChecker.getOrderSkuList();
        for (OrderSku orderSku : orderSkuList) {
            int result = this.skuRepository.reduceStock(orderSku.getId(), orderSku.getCount());
            if (result != 1) {
                throw new ParameterException(50003);
            }
        }
    }

    private void writeOffCoupon(Long couponId, Long orderId, Long uid) {
        int result = this.userCouponRepository.writeOff(couponId, orderId, uid);
        if (result != 1) {
            throw new ForbiddenException(40012);
        }
    }
}
