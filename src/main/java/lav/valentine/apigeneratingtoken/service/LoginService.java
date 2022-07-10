package lav.valentine.apigeneratingtoken.service;

import lav.valentine.apigeneratingtoken.dto.LoginDto;
import lav.valentine.apigeneratingtoken.dto.TokenDto;

/**
 * The class is intended for user authentication and registration
 */
public interface LoginService {
    TokenDto userAuthentication(LoginDto loginDto);
    Boolean userRegistration(LoginDto loginDto);
}