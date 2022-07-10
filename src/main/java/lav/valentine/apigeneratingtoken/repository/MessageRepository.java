package lav.valentine.apigeneratingtoken.repository;

import lav.valentine.apigeneratingtoken.entity.Message;
import lav.valentine.apigeneratingtoken.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * A class for working with data from the 'message' database table
 */
@Repository
public interface MessageRepository extends CrudRepository<Message, String> {
    List<Message> findAllByUser(User user);
}