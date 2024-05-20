package com.ssafy.config;

import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;


@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info().title("EnjoyTrip API 명세서").description(
                        "<h3>SSAFY API Reference for Developers</h3>EnjoyTrip API<br><img src=\"/assets/img/ssafy_logo.png\" width=\"150\">")
                .version("v1").contact(new io.swagger.v3.oas.models.info.Contact().name("hissam")
                        .email("hissam@ssafy.com").url("http://edu.ssafy.com"));
        // SecuritySecheme명
        String jwtSchemeName = "jwtAuth";
        // API 요청헤더에 인증정보 포함
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);
        // SecuritySchemes 등록
        Components components = new Components()
                .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
                        .name(jwtSchemeName)
                        .type(SecurityScheme.Type.HTTP) // HTTP 방식
                        .scheme("bearer"));
        출처:
        https:
//happy-jjang-a.tistory.com/165 [jjang-a 블로그:티스토리]
        return new OpenAPI()
                .components(components)
                .info(info)
                .addSecurityItem(securityRequirement);
    }

    @Bean
    public GroupedOpenApi tripApi() {
        return GroupedOpenApi.builder().group("trip").pathsToMatch("/trip/**").build();
    }

    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder().group("user").pathsToMatch("/user/**").build();
    }


}