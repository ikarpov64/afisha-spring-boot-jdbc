package org.javaacademy.afisha.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Configuration
@ConfigurationProperties(prefix = "ticket")
public class TicketQuantitiesConfig {
    private final Map<String, Integer> quantities = new LinkedHashMap<>();
}
