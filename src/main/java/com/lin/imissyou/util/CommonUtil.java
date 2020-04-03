package com.lin.imissyou.util;

import com.lin.imissyou.bo.PageCounter;

import java.util.Calendar;
import java.util.Date;

public class CommonUtil {

    public static PageCounter covertToPageParameter(Integer start, Integer count) {
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
}
