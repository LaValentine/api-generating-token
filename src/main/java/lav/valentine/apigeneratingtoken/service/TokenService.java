package lav.valentine.apigeneratingtoken.service;

public interface TokenService {
    String generateToken(String username);

    Boolean tokenIsValid(String token, String username);
}