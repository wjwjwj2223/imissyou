package com.lin.imissyou.logic;

import com.lin.imissyou.bo.SkuOrderBO;
import com.lin.imissyou.dto.OrderDTO;
import com.lin.imissyou.dto.SkuInfoDTO;
import com.lin.imissyou.exception.http.ParameterException;
import com.lin.imissyou.model.OrderSku;
import com.lin.imissyou.model.Sku;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@NoArgsConstructor
public class OrderChecker {

    @Setter
    private OrderDTO orderDTO;
    @Setter
    private List<Sku> serverSkuList;
    @Setter
    private CouponChecker couponChecker;
    @Getter
    private List<OrderSku> orderSkuList = new ArrayList<>();

    @Value("${missyou.order.max-sku-limit}")
    private int maxSkuLimit;

//    public OrderChecker(OrderDTO orderDTO, List<Sku> serverSkuList, CouponChecker couponChecker) {
//        this.orderDTO = orderDTO;
//        this.serverSkuList = serverSkuList;
//        this.couponChecker = couponChecker;
//    }

    public String getLeaderImg() {
        return this.serverSkuList.get(0).getImg();
    }

    public String getLeaderTitle() {
        return this.serverSkuList.get(0).getTitle();
    }

    public Integer getTotalCount() {
       return this.orderDTO.getSkuInfoList().stream()
                .map(skuInfoDTO -> skuInfoDTO.getCount() )
                .reduce(Integer::sum)
                .orElse(0);
    }

    public void isOk() {
        //orderTotalPrice serverTotalPrice
        //下架
        //售罄
        //购买数量超出库存
        //超出sku可购买数量上限
        //优惠券 couponChecker

        BigDecimal serverTotalPrice = new BigDecimal("0");
        List<SkuOrderBO> skuOrderBOList = new ArrayList<>();
        this.skuNotOnSale(orderDTO.getSkuInfoList().size(), serverSkuList.size());

        for (int i = 0; i < this.serverSkuList.size(); i++) {
            Sku sku = this.serverSkuList.get(i);
            SkuInfoDTO skuInfoDTO = this.orderDTO.getSkuInfoList().get(i);
            this.containsSoldOutSku(sku);
            this.beyondSkuStock(sku, skuInfoDTO);
            this.beyondMaxSkuLimit(skuInfoDTO);

            serverTotalPrice = serverTotalPrice.add(this.calculateSkuOrderPrice(sku, skuInfoDTO));
            skuOrderBOList.add(new SkuOrderBO(sku, skuInfoDTO));

            this.orderSkuList.add(new OrderSku(sku, skuInfoDTO));
        }
        this.totalPriceIsOk(orderDTO.getTotalPrice(), serverTotalPrice);
        if (this.couponChecker != null) {
            this.couponChecker.isOk();
            this.couponChecker.canbeUsed(skuOrderBOList, serverTotalPrice);
            this.couponChecker.finalTotalPriceIsOk(orderDTO.getFinalTotalPrice(),serverTotalPrice);
        }
    }

    private BigDecimal calculateSkuOrderPrice(Sku sku, SkuInfoDTO skuInfoDTO) {
        if (skuInfoDTO.getCount() <= 0) {
            throw new ParameterException(50007);
        }
        return sku.getActualPrice().multiply(new BigDecimal(skuInfoDTO.getCount()));
    }

    private void totalPriceIsOk(BigDecimal orderTotalPrice, BigDecimal serverTotalPrice) {
        if (orderTotalPrice.compareTo(serverTotalPrice) != 0) {
            throw new ParameterException(50005);
        }
    }

    //是否有下架商品
    //判断本地查找出来的skulist 和前端传入的skulist数量一致
    private void skuNotOnSale(int count1, int count2) {
        if (count1 != count2) {
            throw new ParameterException(50002);
        }
    }
    //是否售罄
    private void containsSoldOutSku(Sku sku) {
        if (sku.getStock() == 0) {
            throw new ParameterException(50001);
        }
    }
    //是否超卖
    private void beyondSkuStock(Sku sku, SkuInfoDTO skuInfoDTO) {
        if (sku.getStock() < skuInfoDTO.getCount()) {
            throw new ParameterException(50003);
        }
    }
    //超出购买数量限制
    private void beyondMaxSkuLimit(SkuInfoDTO skuInfoDTO) {
        if (skuInfoDTO.getCount() > maxSkuLimit) {
            throw new ParameterException(50004);
        }
    }
}
