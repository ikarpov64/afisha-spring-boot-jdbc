package org.javaacademy.afisha.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI myOpenApi() {
        Server server = new Server();
        server.setUrl("http://localhost:8080");
        server.setDescription("Рабочий сервер");

        Info info = new Info()
                .title("Api Сервиса по покупке билетов")
                .version("1.0")
                .description("Апи по созданию мероприятий, созданию мест проведения мероприятий. \n"
                        + "А также сервис по генерации и продажи билетов и генерации отчета по проданным билетам.");

        return new OpenAPI()
                .info(info)
                .servers(List.of(server));
    }
}
