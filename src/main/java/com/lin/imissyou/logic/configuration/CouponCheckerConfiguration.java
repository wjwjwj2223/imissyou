package com.lin.imissyou.logic.configuration;

import com.lin.imissyou.logic.CouponChecker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class CouponCheckerConfiguration {


    @Bean
    @Scope("prototype")
    CouponChecker getCouponChecker() {
        return new CouponChecker();
    }
}