package lav.valentine.apigeneratingtoken.exception;

import org.springframework.http.HttpStatus;

public class NotExistException extends ApiException {

    public NotExistException(String message) {
        super(message);
        super.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }
}