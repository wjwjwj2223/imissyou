package com.lin.imissyou.util;

import com.lin.imissyou.bo.PageCounter;

public class CommonUtil {

    public static PageCounter covertToPageParameter(Integer start, Integer count) {
        int pageNum = start / count;
        PageCounter pageCounter = PageCounter.builder()
                .page(pageNum)
                .count(count)
                .build();
        return pageCounter;
    }

}
