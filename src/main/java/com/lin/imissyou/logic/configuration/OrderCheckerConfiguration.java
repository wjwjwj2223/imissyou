package com.lin.imissyou.logic.configuration;

import com.lin.imissyou.logic.OrderChecker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class OrderCheckerConfiguration {

    @Bean
    @Scope("prototype")
    OrderChecker getChecker() {
        return new OrderChecker();
    }
}
