package com.lin.imissyou.api.v1;

import com.lin.imissyou.core.interceptors.ScopeLevel;
import com.lin.imissyou.lib.LinWxNotify;
import com.lin.imissyou.services.WxPaymentNotifyService;
import com.lin.imissyou.services.WxPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Positive;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@RestController
@RequestMapping("payment")
@Validated
public class PaymentController {

    @Autowired
    WxPaymentService wxPaymentService;
    @Autowired
    WxPaymentNotifyService wxPaymentNotifyService;

    @PostMapping("/pay/order/{id}")
    @ScopeLevel
    public Map<String,String> preWxOrder(@PathVariable(name = "id") @Positive Long oid) {
        Map<String, String> miniPayParams = this.wxPaymentService.preOrder(oid);
        return miniPayParams;
    }

    @RequestMapping("/wx/notify")
    public String payCallback(HttpServletRequest request,
                              HttpServletResponse response) {
        InputStream s;
        try {
            s = request.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return LinWxNotify.fail();
        }
        String xml;
        xml = LinWxNotify.readNotify(s);
        wxPaymentNotifyService.processPayNotify(xml);
        return LinWxNotify.success();
    }
}
