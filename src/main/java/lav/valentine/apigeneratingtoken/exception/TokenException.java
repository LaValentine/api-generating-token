package lav.valentine.apigeneratingtoken.exception;

import org.springframework.http.HttpStatus;

public class TokenException extends ApiException {

    public TokenException(String message) {
        super(message);
        super.httpStatus = HttpStatus.BAD_REQUEST;
    }
}