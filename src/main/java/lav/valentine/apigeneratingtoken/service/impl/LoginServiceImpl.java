package lav.valentine.apigeneratingtoken.service.impl;

import lav.valentine.apigeneratingtoken.dto.LoginDto;
import lav.valentine.apigeneratingtoken.dto.TokenDto;
import lav.valentine.apigeneratingtoken.entity.User;
import lav.valentine.apigeneratingtoken.exception.WrongPasswordException;
import lav.valentine.apigeneratingtoken.service.LoginService;
import lav.valentine.apigeneratingtoken.service.TokenService;
import lav.valentine.apigeneratingtoken.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * The class is intended for user authentication
 */
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

    /**
     * The method authenticates the user
     * @param loginDto The method that generates the jwt token
     * @return Generated jwt token
     */
    @Override
    public TokenDto userAuthentication(LoginDto loginDto) {
        User user = userService.getUserByName(loginDto.getName());

        if (user.getPassword().equals(loginDto.getPassword())) {
            return new TokenDto(tokenService.generateToken(user.getName()));
        }
        else {
            log.warn("Entered wrong password for " + loginDto.getName());
            throw new WrongPasswordException(USER_WRONG_PASSWORD);
        }
    }
}