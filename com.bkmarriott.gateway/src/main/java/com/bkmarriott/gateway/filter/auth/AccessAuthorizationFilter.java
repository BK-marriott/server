package com.bkmarriott.gateway.filter.auth;

import static com.bkmarriott.gateway.filter.auth.AccessTokenAuthenticationFilter.*;

import com.bkmarriott.gateway.util.RolePermission;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Component
public class AccessAuthorizationFilter implements GlobalFilter, Ordered {

    private final RolePermission rolePermission;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        String path = request.getURI().getPath();
        HttpMethod method = request.getMethod();
        String role = request.getHeaders().getFirst(HEADER_ROLE);
        log.debug("[AccessAuthorizationFilter] [filter] path ::: {}, method ::: {}, role ::: {}", path, method, role);

        if (rolePermission.isAllowed(role, path, method)) {
            return chain.filter(exchange);
        }
        return handleAccessDenied(exchange);
    }

    private Mono<Void> handleAccessDenied(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.FORBIDDEN);
        log.error("[AccessAuthorizationFilter] [filter] Access denied for path ::: {}, method ::: {}",
            exchange.getRequest().getURI().getPath(),
            exchange.getRequest().getMethod());
        return response.setComplete();
    }

    @Override
    public int getOrder() {
        return AUTHENTICATION_FILTER_ORDER + 1;
    }
}
