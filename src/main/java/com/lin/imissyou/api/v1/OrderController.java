package com.lin.imissyou.api.v1;

import com.lin.imissyou.bo.PageCounter;
import com.lin.imissyou.core.LocalUser;
import com.lin.imissyou.core.interceptors.ScopeLevel;
import com.lin.imissyou.dto.OrderDTO;
import com.lin.imissyou.logic.OrderChecker;
import com.lin.imissyou.model.Order;
import com.lin.imissyou.services.OrderService;
import com.lin.imissyou.util.CommonUtil;
import com.lin.imissyou.vo.OrderIdVO;
import com.lin.imissyou.vo.OrderSimplifyVO;
import com.lin.imissyou.vo.PagingDozer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Autowired
    OrderService orderService;

    @Value("${missyou.order.pay-time-limmit}")
    private Long payTimeLimit;

    @PostMapping("")
    @ScopeLevel()
    public OrderIdVO placeOrder(@RequestBody OrderDTO orderDTO) {
        Long uid = LocalUser.getUser().getId();
        OrderChecker orderChecker = orderService.isOk(uid, orderDTO);
        Long oid = this.orderService.placeOrder(uid, orderDTO, orderChecker);
        return new OrderIdVO(oid);
    }


    @ScopeLevel
    @GetMapping("/status/unpaid")
    @SuppressWarnings("unchecked")
    public PagingDozer getUnpaid(@RequestParam(defaultValue = "0")
                                         Integer start,
                                 @RequestParam(defaultValue = "10")
                                         Integer count) {
        PageCounter page = CommonUtil.covertToPageParameter(start, count);
        Page<Order> orderPage = this.orderService.getUnpaid(page.getPage(), page.getCount());
        PagingDozer pagingDozer = new PagingDozer(orderPage, OrderSimplifyVO.class);
        pagingDozer.getItems().forEach((o) -> ((OrderSimplifyVO)o).setPeriod(this.payTimeLimit));
        return pagingDozer;
    }

}
