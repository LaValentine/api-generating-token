package lav.valentine.apigeneratingtoken.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lav.valentine.apigeneratingtoken.exception.TokenException;
import lav.valentine.apigeneratingtoken.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;

@Slf4j
@Service
public class JwtTokenServiceImpl implements TokenService {
    @Value("${app.jwt-secret}")
    private String JWT_SECRET;

    @Value("${exception.token.is-null}")
    private String TOKEN_IS_NULL;
    @Value("${exception.token.for-another-user}")
    private String TOKEN_FOR_ANOTHER_USER;
    @Value("${exception.token.unknown-exception}")
    private String TOKEN_UNKNOWN_EXCEPTION;

    @Override
    public String generateToken(String username) {
        log.info("Generating token for " + username);
        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes());

        return Jwts.builder()
                .claim("sub", username)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public Boolean tokenIsValid(String token, String username) {
        log.info("Chek token \"" + token + "\" for " + username);

        if (token == null || token.equals("")) {
            log.warn(TOKEN_IS_NULL);
            throw new TokenException(TOKEN_IS_NULL);
        }

        String tokenUsername;

        try {
            byte[] secretBytes = JWT_SECRET.getBytes();

            Jws<Claims> jwsClaims = Jwts.parserBuilder()
                    .setSigningKey(secretBytes)
                    .build()
                    .parseClaimsJws(token.replace("Bearer_", ""));

            tokenUsername = jwsClaims.getBody()
                    .getSubject();
        }
        catch (Exception ex) {
            log.error(TOKEN_UNKNOWN_EXCEPTION);
            throw new TokenException(TOKEN_UNKNOWN_EXCEPTION);
        }

        if (!tokenUsername.equals(username)) {
            log.warn(TOKEN_FOR_ANOTHER_USER);
            throw new TokenException(TOKEN_FOR_ANOTHER_USER);
        }

        return true;
    }
}