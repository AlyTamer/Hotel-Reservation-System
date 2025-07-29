package com.aly.brightskies.task3.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info =@Info(
                title = "Bright Skies Task 3 API",
                version = "1.0",
                description = "API documentation for Bright Skies Task 3 to test out the Hotel Management System"
        ),

        servers = {
                @Server(
                url = "http://localhost:8080",
                description = "Local server"
        ),
        @Server(
                url="http://localhost:8888",
                description = "Local server for dockerized app"
        )},
        security = @SecurityRequirement(
                name = "bearerAuth"
        )


)
@SecurityScheme(
        name="bearerAuth",
        description = "Basic JWT Authentication based on Username and Password + Admin/User Role",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER

)
public class OpenApiConfig {
}
