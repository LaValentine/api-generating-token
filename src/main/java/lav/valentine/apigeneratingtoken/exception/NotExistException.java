package lav.valentine.apigeneratingtoken.exception;

import org.springframework.http.HttpStatus;

/**
 * Exceptions that occur as a result of processing a request sent from an unregistered user
 */
public class NotExistException extends ApiException {

    public NotExistException(String message) {
        super(message);
        super.httpStatus = HttpStatus.BAD_REQUEST;
    }
}