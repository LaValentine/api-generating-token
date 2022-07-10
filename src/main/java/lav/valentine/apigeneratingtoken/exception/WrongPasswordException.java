package lav.valentine.apigeneratingtoken.exception;

import org.springframework.http.HttpStatus;

/**
 * Exceptions to occur as a result of processing an authentication request when the password does not match
 */
public class WrongPasswordException extends ApiException {

    public WrongPasswordException(String message) {
        super(message);
        super.httpStatus = HttpStatus.UNAUTHORIZED;
    }
}