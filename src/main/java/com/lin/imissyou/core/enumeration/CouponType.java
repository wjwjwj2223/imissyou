package com.lin.imissyou.core.enumeration;

import java.util.stream.Stream;

public enum CouponType {

    //满减券
    FULL_MINUS(1, "满减券"),
    //满减折扣券
    FULL_OFF(2, "满减折扣券"),
    //无门槛减除券
    NO_THRESHOLD_MINUS(3, "无门槛减除券");

    private int value;

    private CouponType(int value, String description) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static CouponType toType(int value) {
        return Stream.of(CouponType.values())
                .filter(c-> c.value == value)
                .findAny()
                .orElse(null);
    }
}
