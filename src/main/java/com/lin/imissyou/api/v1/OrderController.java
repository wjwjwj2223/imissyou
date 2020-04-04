package com.lin.imissyou.api.v1;

import com.lin.imissyou.bo.PageCounter;
import com.lin.imissyou.core.LocalUser;
import com.lin.imissyou.core.interceptors.ScopeLevel;
import com.lin.imissyou.dto.OrderDTO;
import com.lin.imissyou.exception.http.NotFoundException;
import com.lin.imissyou.logic.OrderChecker;
import com.lin.imissyou.model.Order;
import com.lin.imissyou.services.OrderService;
import com.lin.imissyou.util.CommonUtil;
import com.lin.imissyou.vo.OrderIdVO;
import com.lin.imissyou.vo.OrderPureVO;
import com.lin.imissyou.vo.OrderSimplifyVO;
import com.lin.imissyou.vo.PagingDozer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    @GetMapping("/detail/{id}")
    public OrderPureVO getOrderDetail(@PathVariable(name = "id") Long oid) {
        Optional<Order> orderOptional = this.orderService.getOrderDetail(oid);
        return orderOptional.map((o) -> new OrderPureVO(o, payTimeLimit))
                .orElseThrow(() -> new NotFoundException(50009));
    }

    @ScopeLevel
    @GetMapping("/status/unpaid")
    @SuppressWarnings("unchecked")
    public PagingDozer getUnpaid(@RequestParam(defaultValue = "0")
                                         Integer start,
                                 @RequestParam(defaultValue = "10")
                                         Integer count) {
        PageCounter page = CommonUtil.convertToPageParameter(start, count);
        Page<Order> orderPage = this.orderService.getUnpaid(page.getPage(), page.getCount());
        PagingDozer pagingDozer = new PagingDozer(orderPage, OrderSimplifyVO.class);
        pagingDozer.getItems().forEach((o) -> ((OrderSimplifyVO)o).setPeriod(this.payTimeLimit));
        return pagingDozer;
    }

    @ScopeLevel
    @GetMapping("/by/status/{status}")
    public PagingDozer getByStatus(@PathVariable int status,
                                   @RequestParam(name = "start", defaultValue = "0")
                                           Integer start,
                                   @RequestParam(name = "count", defaultValue = "10")
                                           Integer count) {
        PageCounter page = CommonUtil.convertToPageParameter(start, count);
        Page<Order> paging = this.orderService.getByStatus(status, page.getPage(), page.getCount());
        PagingDozer pagingDozer = new PagingDozer<>(paging, OrderSimplifyVO.class);
        pagingDozer.getItems().forEach(o -> ((OrderSimplifyVO) o).setPeriod(this.payTimeLimit));
        return pagingDozer;
    }

}
