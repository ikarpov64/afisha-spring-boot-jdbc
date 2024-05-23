package org.javaacademy.afisha.config;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
public class DatabaseConfig {

    @SneakyThrows
    @Bean
    public Connection connection(DataSource dataSource) {
        return dataSource.getConnection();
    }
}
