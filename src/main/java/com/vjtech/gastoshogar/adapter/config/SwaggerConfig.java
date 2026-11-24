package com.vjtech.gastoshogar.adapter.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private static final String BEARER_SCHEME = "bearerAuth";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Gasto Hogar API")
                .version("1.0.0")
                .description("""
                    API REST de apoyo para la app web de control de gastos.

                    Módulos disponibles:

                    - **Auth** (`/api/v1/auth`): registro, login y usuario autenticado.
                    - **Usuarios** (`/api/v1/usuarios`): CRUD de usuarios.
                    - **Membresías** (`/api/v1/membresias`): planes mensual/anual.
                    - **Respaldos** (`/api/v1/respaldos`): guarda y recupera el estado completo de la app web.

                    Obtén un token en `POST /api/v1/auth/register` o `POST /api/v1/auth/login`
                    y luego usa el botón **Authorize** con ese token (sin la palabra `Bearer`)
                    para probar los endpoints protegidos.

                    Los respaldos requieren membresía activa. El payload es un JSON opaco
                    que la app web arma con todo su estado local.
                    """)
                .contact(new Contact().name("VJTech").email("contact@vjtech.com"))
            )
            .components(new Components().addSecuritySchemes(BEARER_SCHEME,
                new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")
                    .description("Token JWT devuelto por /auth/login o /auth/register")))
            .addSecurityItem(new SecurityRequirement().addList(BEARER_SCHEME));
    }
}
