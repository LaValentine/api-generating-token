package lav.valentine.apigeneratingtoken.repository;

import lav.valentine.apigeneratingtoken.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * A class for working with data from the 'user' database table
 */
@Repository
public interface UserRepository extends CrudRepository<User, String> {
    Optional<User> findByName(String name);
}