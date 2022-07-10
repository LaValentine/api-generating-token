package lav.valentine.apigeneratingtoken.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Exceptions that occur as a result of processing user requests
 */
@Getter
public abstract class ApiException extends RuntimeException {

    protected HttpStatus httpStatus;

    public ApiException(String message) {
        super(message);
    }
}