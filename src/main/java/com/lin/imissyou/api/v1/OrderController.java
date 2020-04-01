package com.lin.imissyou.api.v1;

import com.lin.imissyou.core.LocalUser;
import com.lin.imissyou.core.interceptors.ScopeLevel;
import com.lin.imissyou.dto.OrderDTO;
import com.lin.imissyou.vo.OrderIdVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
@Validated
public class OrderController {

    //商品  无货
    //商品最大购买数  总数量限制
    //SKU 单品购买数限制
    //totalPrice
    //finalTotalPrice
    //是否拥有这个优惠券
    //优惠券是否过期

    @PostMapping("")
    @ScopeLevel()
    public OrderIdVO placeOrder(@RequestBody OrderDTO orderDTO) {
        Long uid = LocalUser.getUser().getId();
        return null;
    }

}
