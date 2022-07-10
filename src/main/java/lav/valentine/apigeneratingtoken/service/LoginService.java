package lav.valentine.apigeneratingtoken.service;

import lav.valentine.apigeneratingtoken.dto.LoginDto;
import lav.valentine.apigeneratingtoken.dto.TokenDto;

/**
 * The class is intended for user authentication
 */
public interface LoginService {
    TokenDto userAuthentication(LoginDto loginDto);
}