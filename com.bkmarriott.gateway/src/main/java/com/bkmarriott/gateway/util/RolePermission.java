package com.bkmarriott.gateway.util;

import java.util.List;
import java.util.Map;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class RolePermission {

    private static final Map<String, List<AllowedPath>> PERMISSIONS;
    private static final List<AllowedPath> PUBLIC_PERMISSIONS;

    static {
        PERMISSIONS = Map.of(
            "MASTER", List.of(

            ),
            "MANAGER", List.of(

            ),
            "STAFF", List.of(

            ),
            "CUSTOMER", List.of(

            )
        );

        PUBLIC_PERMISSIONS = List.of(
            // auth-service
            AllowedPath.of("/api/v1/auth/sign-up", HttpMethod.POST), // 회원가입
            AllowedPath.of("/api/v1/auth/sign-in", HttpMethod.POST), // 로그인
            AllowedPath.of("/api/v1/auth/user/*", HttpMethod.GET)   // 회원 단건 조회
        );
    }

    public boolean isAllowed(String role, String path, HttpMethod method) {
        if (true) return true; // 임시 코드, 모든 권한 통과

        if (isPublicPermission(path, method)) {
            return true;
        }
        return isAllowedPermission(role, path, method);
    }

    private boolean isPublicPermission(String path, HttpMethod method) {
        return PUBLIC_PERMISSIONS.stream()
            .anyMatch(allowedPath -> allowedPath.matches(path, method));
    }

    private boolean isAllowedPermission(String role, String path, HttpMethod method) {
        return PERMISSIONS.getOrDefault(role, List.of()).stream()
            .anyMatch(allowedPath -> allowedPath.matches(path, method));
    }

    public static class AllowedPath {

        private final String regexPattern;
        private final HttpMethod method; // HTTP Method

        public AllowedPath(String pathPattern, HttpMethod method) {
            // Path 패턴
            this.regexPattern = convertToRegex(pathPattern);
            this.method = method;
        }

        public static AllowedPath of(String pathPattern, HttpMethod method) {
            return new AllowedPath(pathPattern, method);
        }

        public boolean matches(String path, HttpMethod method) {
            return path.matches(regexPattern) && this.method == method;
        }

        private String convertToRegex(String pathPattern) {
            // Path 패턴을 정규식으로 변환 (e.g., "/user/**" -> "/user/.*")
            return pathPattern
                .replace("**", ".*")
                .replace("*", "[^/]+");
        }
    }
}
