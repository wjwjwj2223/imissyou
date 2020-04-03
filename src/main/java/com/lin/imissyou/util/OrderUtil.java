package com.lin.imissyou.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OrderUtil {

    private static String[] yearCodes;

    @Value("${imissyou.year-codes}")
    public void setYearCodes(String yearCodes) {
        String[] chars = yearCodes.split(",");
        OrderUtil.yearCodes = chars;
    }

    public static String makeOrderNo() {
        return null;
    }

}
