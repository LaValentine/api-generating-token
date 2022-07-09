package lav.valentine.apigeneratingtoken.service;

import lav.valentine.apigeneratingtoken.dto.LoginRequestDto;
import lav.valentine.apigeneratingtoken.dto.TokenResponseDto;

public interface LoginService {
    TokenResponseDto generateToken(LoginRequestDto loginRequestDto);
}