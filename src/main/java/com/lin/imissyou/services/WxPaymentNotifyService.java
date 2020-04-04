package com.lin.imissyou.services;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.lin.imissyou.core.enumeration.OrderStatus;
import com.lin.imissyou.exception.http.ServerErrorException;
import com.lin.imissyou.model.Order;
import com.lin.imissyou.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class WxPaymentNotifyService {

    @Autowired
    private WxPaymentService wxPaymentService;

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public void processPayNotify(String data) {
        Map<String, String> dataMap = new HashMap<>();
        try {
            dataMap = WXPayUtil.xmlToMap(data);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerErrorException(9999);
        }

        WXPay wxPay = this.wxPaymentService.assembleWxPayConfig();
        boolean valid;
        try {
            valid = wxPay.isResponseSignatureValid(dataMap);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerErrorException(9999);
        }
        if (!valid) {
            throw new ServerErrorException(9999);
        }

        String returnCode = dataMap.get("return_code");
        String orderNo = dataMap.get("out_trade_no");
        String resultCode = dataMap.get("result_code");

        if (!returnCode.equals("SUCCESS")) {
            throw new ServerErrorException(9999);
        }
        if (!resultCode.equals("SUCCESS")) {
            throw new ServerErrorException(9999);
        }
        if (orderNo == null) {
            throw new ServerErrorException(9999);
        }
        this.deal(orderNo);
    }

    private void deal(String orderNo) {
        Optional<Order> orderOptional = this.orderRepository.findFirstByOrderNo(orderNo);
        Order order = orderOptional.orElseThrow(() -> new ServerErrorException(9999));
        // unpaid -> paid
        // 支付后扣除
        // 3/3
        // unpaid -> paid
        // paid -> paid
        // Cancel
        //
        int res = -1;
        if (order.getStatus().equals(OrderStatus.UNPAID.getValue())
                || order.getStatus().equals(OrderStatus.CANCELED.getValue())) {
            res = this.orderRepository.updateStatusByOrderNo(orderNo, OrderStatus.PAID.getValue());
        }
        if (res != 1) {
            throw new ServerErrorException(9999);
        }


    }
}
