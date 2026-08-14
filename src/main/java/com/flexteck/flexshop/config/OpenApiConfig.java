package com.flexteck.flexshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI flexShopApi() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Flexshop E-commerce Backend API")
                                .version("1.0.0")
                                .description(
                                        """
                                                FlexShop is a professional e-commerce backend API built with Java and Spring Boot.

                                                Current features:
                                                - Category management
                                                - Product management
                                                - Product search
                                                - Product filtering by category

                                                Upcoming features:
                                                - User authentication
                                                - JWT security
                                                - Order management
                                                - Role-based access control
                                                """)
                                .contact(new Contact()
                                        .name("Anthony Kanu")
                                        .email("flexteckse@gmail.com")
                                        .url("https://github.com/kingandrenz"))
                                .license(new License()
                                        .name("MIT License")
                                        .url("https://opensource.org/licenses/MIT")));
    }

}
