package lav.valentine.apigeneratingtoken.service;

import lav.valentine.apigeneratingtoken.entity.User;
import lav.valentine.apigeneratingtoken.exception.NotExistException;
import lav.valentine.apigeneratingtoken.repository.UserRepository;
import lav.valentine.apigeneratingtoken.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {

    @MockBean
    private UserRepository userRepository;
    @Autowired
    private UserServiceImpl userService;

    private static final UUID USER_ID = UUID.randomUUID();
    private static final String USERNAME = "user";
    private static final String PASSWORD = "password";

    @Test
    void getUserByName() {
        User user = new User(USER_ID, USERNAME, PASSWORD);
        when(userRepository.findByName(USERNAME)).thenReturn(Optional.of(user));

        User foundUser = userService.getUserByName(USERNAME);

        assertNotNull(foundUser);

        assertEquals(user.getUserId(), foundUser.getUserId());
        assertEquals(user.getName(), foundUser.getName());
        assertEquals(user.getPassword(), foundUser.getPassword());
    }

    @Test
    void getUserByNameNotExist() {
        when(userRepository.findByName(any())).thenReturn(Optional.empty());

        assertThrows(NotExistException.class, () -> userService.getUserByName(USERNAME));
    }

    @Test
    void saveUser() {
        User user = new User(USER_ID, USERNAME, PASSWORD);
        when(userRepository.findByName(any())).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(user);

        User returnedUser = userService.saveUser(USERNAME, PASSWORD);

        assertNotNull(returnedUser);
        assertEquals(returnedUser.getName(), USERNAME);
        assertEquals(returnedUser.getPassword(), PASSWORD);
    }

    @Test
    void saveUserWhenUserAlreadyExist() {
        User user = new User(USER_ID, USERNAME, PASSWORD);
        when(userRepository.findByName(any())).thenReturn(Optional.of(user));

        assertNull(userService.saveUser(USERNAME, PASSWORD));
    }
}