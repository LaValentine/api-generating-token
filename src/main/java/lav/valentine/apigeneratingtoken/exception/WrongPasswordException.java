package lav.valentine.apigeneratingtoken.exception;

import org.springframework.http.HttpStatus;

public class WrongPasswordException extends ApiException {

    public WrongPasswordException(String message) {
        super(message);
        super.httpStatus = HttpStatus.UNAUTHORIZED;
    }
}