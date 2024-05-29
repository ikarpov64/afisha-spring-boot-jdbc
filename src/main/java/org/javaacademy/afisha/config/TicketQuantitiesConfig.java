package org.javaacademy.afisha.config;

import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
@ConfigurationProperties(prefix = "ticket")
public class TicketQuantitiesConfig {
    private final Map<String, Integer> quantities = new LinkedHashMap<>();
}
