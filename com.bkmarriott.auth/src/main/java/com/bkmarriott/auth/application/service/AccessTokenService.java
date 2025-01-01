package com.bkmarriott.auth.application.service;

import com.bkmarriott.auth.application.util.dategnerator.DateGenerator;
import com.bkmarriott.auth.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AccessTokenService {

    private static final String TOKEN_DELIMITER = " ";
    private static final String CLAIM_NAME_USER_ID = "actorId";
    private static final String CLAIM_NAME_ROLE = "role";
    private static final String TOKEN_PREFIX = "Bearer";


    private final DateGenerator dateGenerator;
    private final long tokenExpiration;
    private final SecretKey secretKey;

    public AccessTokenService(
        DateGenerator dateGenerator,
        @Value("${service.jwt.access-expiration}") long tokenExpiration,
        @Value("${service.jwt.secret-key}") String secretKey
    ) {
        this.dateGenerator = dateGenerator;
        this.tokenExpiration = tokenExpiration;
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
    }

    public String generateAccessToken(User user) {
        String token = Jwts.builder()
            .claim(CLAIM_NAME_USER_ID, user.getId())
            .claim(CLAIM_NAME_ROLE, user.getRole().name())
            .expiration(dateGenerator.calcExpireDate(tokenExpiration))
            .signWith(secretKey)
            .compact();

        return String.join(TOKEN_DELIMITER, TOKEN_PREFIX, token);
    }
}
