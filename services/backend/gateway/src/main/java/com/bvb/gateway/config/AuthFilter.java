package com.bvb.gateway.config;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    private final JwtService jwtService;

    public AuthFilter(JwtService jwtService) {
        super(Config.class);
        this.jwtService = jwtService;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (exchange.getRequest().getCookies().getFirst("jwtToken") == null) {
                throw new RuntimeException("Missing cookie");
            }

            ServerHttpRequest request = null;

            String authHeader = exchange.getRequest().getCookies().get("jwtToken").get(0).getValue();

            try {
                // validate token
                jwtService.validateToken(authHeader);

                // check if token is expired?
                if (!jwtService.isTokenExpired(authHeader)) {
                    // because we want to keep the token information
                    // when it is redirected to the microservice
                    request = exchange.getRequest()
                            .mutate()
                            .header("username", jwtService.extractUsername(authHeader))
                            .header("authorities", jwtService.extractAuthorities(authHeader))
                            .header("id", jwtService.extractUserId(authHeader))
                            .build();
                }
            } catch (Exception e) {
                // access deny
                throw new RuntimeException("Access denied");
            }

            // sent the request to the microservice but still with token header
            return chain.filter(exchange.mutate().request(request).build());
        };
    }

    public static class Config {
    }
}
