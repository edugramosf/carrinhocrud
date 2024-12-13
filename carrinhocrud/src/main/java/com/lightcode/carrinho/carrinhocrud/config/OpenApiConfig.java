package com.lightcode.carrinho.carrinhocrud.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@OpenAPIDefinition(info = @Info(
        title = "API Carrinho",
        version = "v1",
        description = "Documentação da API Carrinho"))
@Configuration
@Component
public class OpenApiConfig {
}
