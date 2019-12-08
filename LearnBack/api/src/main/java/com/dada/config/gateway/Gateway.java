package com.dada.config.gateway;

import org.springframework.cloud.gateway.filter.factory.StripPrefixGatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Gateway {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        StripPrefixGatewayFilterFactory.Config config = new StripPrefixGatewayFilterFactory.Config();
        config.setParts(1);
        return builder.routes()
                .route("host_route", r -> r.path("/user/**").filters(f -> f.stripPrefix(1)).uri("http://localhost:9999"))
                .build();
    }
}
