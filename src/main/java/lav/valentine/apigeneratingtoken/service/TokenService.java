package lav.valentine.apigeneratingtoken.service;

/**
 * The class is intended for operations with a token
 */
public interface TokenService {
    String generateToken(String username);

    Boolean tokenIsValid(String token, String username);
}