package com.lin.imissyou.util;

import com.lin.imissyou.bo.PageCounter;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

public class CommonUtil {

    public static PageCounter convertToPageParameter(Integer start, Integer count) {
        int pageNum = start / count;
        PageCounter pageCounter = PageCounter.builder()
                .page(pageNum)
                .count(count)
                .build();
        return pageCounter;
    }

    public static Boolean isInTimeLine(Date now, Date start, Date end) {
        Long nowTime = now.getTime();
        Long startTime = start.getTime();
        Long endTime = end.getTime();
        if (nowTime > startTime && endTime > nowTime) {
            return true;
        }
        return false;
    }

    public static Calendar addSomeSeconods(Calendar calendar, int seconds) {
        calendar.add(Calendar.SECOND, seconds);
        return calendar;
    }

    public static Boolean isOutOfDate(Date expiredTime) {
        Long now = Calendar.getInstance().getTimeInMillis();
        Long expiredTimeStamp = expiredTime.getTime();
        if(now > expiredTimeStamp){
            return true;
        }
        return false;
    }

    public static String timestamp10(){
        Long timestamp13 = Calendar.getInstance().getTimeInMillis();
        String timestamp13Str = timestamp13.toString();
        return timestamp13Str.substring(0, timestamp13Str.length() - 3);
    }

    public static String yuanToFenPlainString(BigDecimal p){
        p = p.multiply(new BigDecimal("100"));
        return CommonUtil.toPlain(p);
    }

    public static String toPlain(BigDecimal p){
        return p.stripTrailingZeros().toPlainString();
    }
}
