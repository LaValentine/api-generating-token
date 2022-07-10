package lav.valentine.apigeneratingtoken.service;

import lav.valentine.apigeneratingtoken.entity.User;

/**
 * The class is designed to work with user data
 */
public interface UserService {
    User getUserByName(String username);
    User saveUser(String username, String password);
}