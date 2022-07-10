package lav.valentine.apigeneratingtoken.exception;

import org.springframework.http.HttpStatus;

/**
 * Exceptions that occur as a result of processing a request sent from an unauthorized user
 */
public class TokenException extends ApiException {

    public TokenException(String message) {
        super(message);
        super.httpStatus = HttpStatus.FORBIDDEN;
    }
}