package lav.valentine.apigeneratingtoken.service;

import lav.valentine.apigeneratingtoken.entity.User;

public interface UserService {
    User getUserByName(String username);
}