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

            // decide whether I want to send the whole token to the microservice
            // meaning I would have to decode it in each microservice
            // but has the benefit of doing role allowed on each endpoints

            // if I filter access to microservice already from the gateway then it is not flexible
            // because maybe in some microservices we will have a specific endpoints allowed for
            // a role but not others

            // so it is better to send the whole token payload to the microservice
            // and the gateway is only there to validate the token and redirect

            ServerHttpRequest request = null;

            // get cookie from request and check if empty
            if (!exchange.getRequest().getCookies().containsKey("accessToken")) {
                throw new RuntimeException("Missing authorization");
            }

            // get cookie from request
            String authHeader = exchange.getRequest().getCookies().get("accessToken").get(0).getValue();

            try {
                // validate token
                jwtService.validateToken(authHeader);

                // check if token is expired?
                // need to send request to auth service for that since it is connected to the user db
                if (!jwtService.isTokenExpired(authHeader)) {
                    // because we want to keep the token information
                    // when it is redirected to the microservice
                    request = exchange.getRequest()
                            .mutate()
                            .header("username", jwtService.extractUsername(authHeader))
                            .header("authorities", jwtService.extractAuthorities(authHeader))
                            .build();
                }
            } catch (Exception e) {
                throw new RuntimeException("Access denied");
            }

            // sent the request to the microservice but still with token header
            return chain.filter(exchange.mutate().request(request).build());
        };
    }

    public static class Config {
    }
}
