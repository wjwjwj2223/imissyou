package com.lin.imissyou.core.enumeration;

import java.util.stream.Stream;

public enum  OrderStatus {
    //全部
    ALL(0,"全部"),
    UNPAID(1,"待支付"),
    PAID(2,"已支付"),
    DELIVERED(3, "已发货"),
    FINISHED(4,"已完成"),
    CANCELED(5,"已取消"),

    PAID_BUT_OUT_OF(21,"已支付,但无货或者库存不足"),
    DEAL_OUT_OF(22,"已处理缺货但支付的情况");

    private int value;
    private OrderStatus(int value, String text) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static OrderStatus toType(int value) {
        return Stream.of(OrderStatus.values())
                .filter(c -> c.value == value)
                .findAny()
                .orElse(null);
    }
}
