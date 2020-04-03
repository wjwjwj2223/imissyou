package com.lin.imissyou.core.configuration;

import com.lin.imissyou.model.TestModel;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class TestConfiguration {

    @Setter
    private String name1;
    @Setter
    private String name2;

    public TestConfiguration() {
        System.out.println("TestConfiguration init");
    }

    @Bean
    @Scope("prototype")
    public TestModel test() {
        System.out.println(Thread.currentThread());
        return new TestModel(name1, name2);
    }
}
