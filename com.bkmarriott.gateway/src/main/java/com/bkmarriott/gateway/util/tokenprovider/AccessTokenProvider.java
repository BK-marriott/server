package com.bkmarriott.gateway.util.tokenprovider;


import static com.bkmarriott.gateway.exception.messages.InvalidTokenErrorMessage.TOKEN_EXPIRED;
import static com.bkmarriott.gateway.exception.messages.InvalidTokenErrorMessage.TOKEN_INVALID;

import com.bkmarriott.gateway.exception.exceptions.InvalidTokenException;
import com.bkmarriott.gateway.util.dategnerator.DateGenerator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AccessTokenProvider {

    private static final String TOKEN_PREFIX = "Bearer";
    private static final String CLAIM_NAME_ACTOR_ID = "actorId";
    private static final String CLAIM_NAME_ROLE = "role";

    private final SecretKey secretKey;
    private final DateGenerator dateGenerator;

    public AccessTokenProvider(
        @Value("${service.jwt.secret-key}") String secretKey,
        DateGenerator dateGenerator
    ) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
        this.dateGenerator = dateGenerator;
    }

    public Actor readToken(String rawData) {
        String token = extractToken(rawData);
        Claims payload = parsePayload(token);
        log.debug("[AccessTokenProvider] [readToken] token ::: {}, payload ::: {}", token, payload);

        validateExpirationDate(payload.getExpiration());

        Long userId = payload.get(CLAIM_NAME_ACTOR_ID, Long.class);
        String role = payload.get(CLAIM_NAME_ROLE, String.class);

        return new Actor(userId, role);
    }

    private String extractToken(String rawData) {
        if (rawData == null || !rawData.startsWith(TOKEN_PREFIX)) {
            throw new InvalidTokenException(TOKEN_INVALID.message());
        }
        return rawData.replaceAll(TOKEN_PREFIX, "").trim();
    }

    private Claims parsePayload(String token) {
        try {
            return Jwts.parser().verifyWith(secretKey).build()
                .parseSignedClaims(token).getPayload();
        } catch (JwtException exception) {
            throw new InvalidTokenException(TOKEN_INVALID.message());
        }
    }

    private void validateExpirationDate(Date expirationDate) {
        Date currentDate = dateGenerator.getCurrentDate();
        if (currentDate.after(expirationDate)) {
            throw new InvalidTokenException(TOKEN_EXPIRED.message());
        }
    }
}
