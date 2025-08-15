package com.kshrd.spring_data_jpa_homework.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Spring Data JPA",
                version = "1.0.0",
                description = "This API provides endpoints for managing customers, products, and orders using Spring Data JPA.",
                contact = @Contact(
                        name = "GitHub",
                        url = "https://github.com/HoeunPichet/03_HOUEN_PICHET_SPRING_DATA_JPA_HOMEWORK.git"
                )
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "Local Server"),
                @Server(url = "http://localhost:8081", description = "Expose Server"),
        }
)

public class SwaggerConfig {
}
