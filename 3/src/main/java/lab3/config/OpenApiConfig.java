package lab3.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("REST API — Учёт студентов")
                        .version("1.0")
                        .description("Лабораторная работа 3. RESTful сервис учёта зачисленных, отчисленных и переведённых студентов по факультетам."));
    }
}
