
package com.lin.imissyou.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class OrderSimplifyVO {
    private Long id;
    private String orderNo;
    private BigDecimal totalPrice;
    private Long totalCount;
    private String snapImg;
    private String snapTitle;
    private BigDecimal finalTotalPrice;
    private Integer status;
    private Long period;
    private Date expiredTime;
    private Date placedTime;
}
