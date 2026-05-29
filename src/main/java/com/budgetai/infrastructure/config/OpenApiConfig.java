package com.budgetai.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                // 🌐 SERVERS AQUI
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Local"),

                        new Server()
                                .url("https://api.budgetai.com")
                                .description("Production")
                ))

                // 📘 INFO
                .info(new Info()
                        .title("💰 Budget AI API")
                        .version("1.0.0")
                        .description("""
                                API inteligente de gerenciamento financeiro com IA.
                                
                                Funcionalidades:
                                - Registro automático de despesas via IA
                                - Processamento de linguagem natural (Spring AI)
                                - Entrada por áudio (STT)
                                - Tools automáticas
                                """)
                        .contact(new Contact()
                                .name("Budget AI Team")
                                .email("support@budgetai.com")
                        )
                        .license(new License()
                                .name("MIT License")
                        )
                );
    }
}