package lav.valentine.apigeneratingtoken.service;

import lav.valentine.apigeneratingtoken.dto.LoginRequestDto;
import lav.valentine.apigeneratingtoken.dto.TokenResponseDto;
import lav.valentine.apigeneratingtoken.entity.User;
import lav.valentine.apigeneratingtoken.exception.TokenException;
import lav.valentine.apigeneratingtoken.exception.WrongPasswordException;
import lav.valentine.apigeneratingtoken.repository.UserRepository;
import lav.valentine.apigeneratingtoken.service.impl.LoginServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class LoginServiceImplTest {
    @MockBean
    private TokenService tokenService;
    @MockBean
    private UserService userService;
    @Autowired
    private LoginServiceImpl loginService;

    private final String USER = "user";
    private final String PASSWORD = "password";
    private final String WRONG_PASSWORD = "wrong-password";
    private final String TOKEN = "token";

    @BeforeEach
    void createUser() {
        User user = new User(any(), USER, PASSWORD);
        when(userService.getUserByName(USER)).thenReturn(user);
    }


    @Test
    void generateToken() {
        when(tokenService.generateToken(any())).thenReturn(TOKEN);

        LoginRequestDto loginRequestDto = new LoginRequestDto(USER, PASSWORD);
        TokenResponseDto tokenResponseDto = loginService.generateToken(loginRequestDto);

        assertNotNull(tokenResponseDto);
        assertEquals(TOKEN, tokenResponseDto.getToken());
    }

    @Test
    void generateTokenWrongPassword() {
        when(tokenService.generateToken(any())).thenReturn(TOKEN);

        LoginRequestDto loginRequestDto = new LoginRequestDto(USER, WRONG_PASSWORD);

        assertThrows(WrongPasswordException.class, () -> loginService.generateToken(loginRequestDto));
    }
}