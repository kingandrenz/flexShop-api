package com.flexteck.flexshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI flexShopOpenAPI() {
        String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                .info(new Info()
                        .title("FlexShop E-commerce Backend API")
                        .version("v1.0")
                        .description("""
                                FlexShop is a professional e-commerce backend API built with Java and Spring Boot.

                                Features:
                                - Category management
                                - Product management
                                - Product search
                                - Product filtering by category
                                - User registration
                                - User login
                                - JWT authentication
                                """)
                        .contact(new Contact()
                                .name("Anthony Kanu")
                                .email("flexteck@gmail.com")
                                .url("https://github.com/kingandrenz"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}
