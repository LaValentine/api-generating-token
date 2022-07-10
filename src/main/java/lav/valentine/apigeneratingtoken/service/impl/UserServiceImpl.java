package lav.valentine.apigeneratingtoken.service.impl;

import lav.valentine.apigeneratingtoken.entity.User;
import lav.valentine.apigeneratingtoken.exception.NotExistException;
import lav.valentine.apigeneratingtoken.repository.UserRepository;
import lav.valentine.apigeneratingtoken.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * The class is designed to work with user data
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Value("${exception.user.not-exist}")
    private String USER_NOT_EXIST;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Getting a user by name
     * @param username User's name
     * @return User
     */
    @Override
    public User getUserByName(String username) {
        log.info("Finding user, where name is " + username);
        return userRepository.findByName(username).orElseThrow(() -> {
            log.warn(USER_NOT_EXIST);
            return new NotExistException(USER_NOT_EXIST);
        });
    }
}