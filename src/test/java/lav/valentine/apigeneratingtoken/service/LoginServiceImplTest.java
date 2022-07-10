package lav.valentine.apigeneratingtoken.service;

import lav.valentine.apigeneratingtoken.dto.LoginDto;
import lav.valentine.apigeneratingtoken.dto.TokenDto;
import lav.valentine.apigeneratingtoken.entity.User;
import lav.valentine.apigeneratingtoken.exception.WrongPasswordException;
import lav.valentine.apigeneratingtoken.service.impl.LoginServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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

    private final static String USER = "user";
    private final static String PASSWORD = "password";
    private final static String WRONG_PASSWORD = "wrong-password";
    private final static String TOKEN = "token";

    @BeforeEach
    void createUser() {
        User user = new User(any(), USER, PASSWORD);
        when(userService.getUserByName(USER)).thenReturn(user);
    }

    @Test
    void generateToken() {
        when(tokenService.generateToken(any())).thenReturn(TOKEN);

        LoginDto loginDto = new LoginDto(USER, PASSWORD);
        TokenDto tokenDto = loginService.userAuthentication(loginDto);

        assertNotNull(tokenDto);
        assertEquals(TOKEN, tokenDto.getToken());
    }

    @Test
    void generateTokenWrongPassword() {
        when(tokenService.generateToken(any())).thenReturn(TOKEN);

        LoginDto loginDto = new LoginDto(USER, WRONG_PASSWORD);

        assertThrows(WrongPasswordException.class, () -> loginService.userAuthentication(loginDto));
    }

    @Test
    void userRegistrationSuccessful() {
        User user = new User(UUID.randomUUID(), USER, PASSWORD);
        LoginDto loginDto = new LoginDto(USER, PASSWORD);

        when(userService.saveUser(any(), any())).thenReturn(user);

        assertTrue(loginService.userRegistration(loginDto));
    }

    @Test
    void userRegistrationUnsuccessful() {
        LoginDto loginDto = new LoginDto(USER, PASSWORD);

        when(userService.saveUser(any(), any())).thenReturn(null);

        assertFalse(loginService.userRegistration(loginDto));
    }
}