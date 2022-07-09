package lav.valentine.apigeneratingtoken.service.impl;

import lav.valentine.apigeneratingtoken.dto.LoginRequestDto;
import lav.valentine.apigeneratingtoken.dto.TokenResponseDto;
import lav.valentine.apigeneratingtoken.entity.User;
import lav.valentine.apigeneratingtoken.exception.WrongPasswordException;
import lav.valentine.apigeneratingtoken.service.LoginService;
import lav.valentine.apigeneratingtoken.service.TokenService;
import lav.valentine.apigeneratingtoken.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {
    @Value("${exception.user.wrong-password}")
    private String USER_WRONG_PASSWORD;

    private final TokenService tokenService;
    private final UserService userService;

    public LoginServiceImpl(TokenService tokenService, UserService userService) {
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @Override
    public TokenResponseDto generateToken(LoginRequestDto loginRequestDto) {
        User user = userService.getUserByName(loginRequestDto.getName());

        if (user.getPassword().equals(loginRequestDto.getPassword())) {
            return new TokenResponseDto(tokenService.generateToken(user.getName()));
        }
        else {
            log.warn("Entered wrong password for " + loginRequestDto.getName());
            throw new WrongPasswordException(USER_WRONG_PASSWORD);
        }
    }
}