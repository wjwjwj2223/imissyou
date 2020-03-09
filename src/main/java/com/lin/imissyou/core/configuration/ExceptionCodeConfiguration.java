package com.lin.imissyou.core.configuration;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "lin")
@PropertySource(value = "classpath:config/exception-code.properties")
@Component
@Setter
public class ExceptionCodeConfiguration {

    private Map<Integer, String> codes = new HashMap<>();
    public String getMessage(int code) {
        return this.codes.get(code);
    }
}
