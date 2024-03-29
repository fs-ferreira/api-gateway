package br.com.fsferreira.configuration;

import org.springdoc.core.models.GroupedOpenApi;
import org.springdoc.core.properties.SwaggerUiConfigParameters;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.reactive.config.EnableWebFlux;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebFlux
public class OpenApiConfiguration {

    @Bean
    @Lazy(false)
    public List<GroupedOpenApi> apis(SwaggerUiConfigParameters parameters, RouteDefinitionLocator locator) {

        var definitions = locator.getRouteDefinitions().collectList().block();

        definitions.stream().filter(
                routeDefinition -> routeDefinition.getId().matches(".*-serivce")
        ).forEach(routeDefinition -> {
            String name = routeDefinition.getId();
            parameters.addGroup(name);
            GroupedOpenApi.builder().pathsToMatch("/" + name + "/**").group(name).build();
        });
        return new ArrayList<>();
    }
}
