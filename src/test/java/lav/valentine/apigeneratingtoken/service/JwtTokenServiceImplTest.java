package lav.valentine.apigeneratingtoken.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lav.valentine.apigeneratingtoken.exception.TokenException;
import lav.valentine.apigeneratingtoken.service.impl.JwtTokenServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class JwtTokenServiceImplTest {

    @Autowired
    private JwtTokenServiceImpl tokenService;

    @Value("${app.jwt-secret}")
    private String JWT_SECRET;
    private static final String USERNAME = "user";
    private static final String ANOTHER_USERNAME = "another_user";

    @Test
    void generateToken() {
        System.out.println(JWT_SECRET);
        String token = tokenService.generateToken(USERNAME);

        byte[] secretBytes = JWT_SECRET.getBytes();

        Jws<Claims> jwsClaims = Jwts.parserBuilder()
                .setSigningKey(secretBytes)
                .build()
                .parseClaimsJws(token);

        String tokenUsername = jwsClaims.getBody()
                .getSubject();

        assertNotNull(token);
        assertEquals(tokenUsername, USERNAME);
    }

    @Test
    void tokenIsValid() {
        String token = tokenService.generateToken(USERNAME);

        assertTrue(tokenService.tokenIsValid(token, USERNAME));
    }

    @Test
    void tokenIsNull() {
        assertThrows(TokenException.class, () -> tokenService.tokenIsValid(null, USERNAME));
        assertThrows(TokenException.class, () -> tokenService.tokenIsValid("", USERNAME));
    }
    @Test
    void tokenForAnotherUser() {
        String token = tokenService.generateToken(ANOTHER_USERNAME);

        assertThrows(TokenException.class, () -> tokenService.tokenIsValid(token, USERNAME));
    }
}