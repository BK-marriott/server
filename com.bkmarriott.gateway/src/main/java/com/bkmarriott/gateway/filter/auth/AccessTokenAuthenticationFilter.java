package com.bkmarriott.gateway.filter.auth;

import com.bkmarriott.gateway.util.tokenprovider.AccessTokenProvider;
import com.bkmarriott.gateway.util.tokenprovider.Actor;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Component
public class AccessTokenAuthenticationFilter implements GlobalFilter, Ordered {

    public static final int AUTHENTICATION_FILTER_ORDER = 100;
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String HEADER_USER_ID = "X-User-Id";
    public static final String HEADER_ROLE = "X-Role";

    public static final List<String> PUBLIC_PATHS;

    static {
        PUBLIC_PATHS = List.of("/api/v1/auth/sign-in", "/api/v1/auth/sign-up");
    }

    private final AccessTokenProvider tokenProvider;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String requestPath = exchange.getRequest().getURI().getPath();
        log.debug("[AccessTokenAuthenticationFilter] [filter] requestPath ::: {}", requestPath);

        if (isPublicUri(requestPath)) {
            return chain.filter(exchange);
        }
        return checkAccessToken(exchange, chain);
    }

    private boolean isPublicUri(String requestPath) {
        return PUBLIC_PATHS.contains(requestPath);
    }

    private Mono<Void> checkAccessToken(ServerWebExchange exchange, GatewayFilterChain chain) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HEADER_AUTHORIZATION);
        Actor actor = tokenProvider.readToken(authHeader);
        log.debug("[AccessTokenAuthenticationFilter] [checkAccessToken] authHeader ::: {} actor ::: {}", authHeader, actor);

        ServerWebExchange exchangeWithActor = getExchangeIncludedActor(exchange, actor);
        return chain.filter(exchangeWithActor);
    }

    private static ServerWebExchange getExchangeIncludedActor(ServerWebExchange exchange, Actor actor) {
        ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
            .header(HEADER_USER_ID, String.valueOf(actor.userId()))
            .header(HEADER_ROLE, String.valueOf(actor.role()))
            .build();

        return exchange.mutate()
            .request(mutatedRequest)
            .build();
    }

    @Override
    public int getOrder() {
        return AUTHENTICATION_FILTER_ORDER;
    }
}
