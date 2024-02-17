package br.com.fsferreira.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
        return builder.routes()
                .route((p) -> p.path("/get").uri("http://httpbin.org:80"))
                .route(p -> p.path("/currency-service/**").uri("lb://currency-service"))
                .route(p -> p.path("/book-service/**").uri("lb://book-service"))
                .build();
    }

}
