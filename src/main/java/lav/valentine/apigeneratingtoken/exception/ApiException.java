package lav.valentine.apigeneratingtoken.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public abstract class ApiException extends RuntimeException {

    protected HttpStatus httpStatus;

    public ApiException(String message) {
        super(message);
    }
}