package lav.valentine.apigeneratingtoken.exception.handler;

import lav.valentine.apigeneratingtoken.exception.ApiException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Class handler exceptions which occur as a result of user's request processing
 */
@ControllerAdvice
public class ApiExceptionHandler {

    /**
     * The method handles exceptions of the ApiException type
     * @param ex Exceptions of the ApiException type
     * @return Error message
     */
    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<String> apiExceptionHandler(ApiException ex) {
        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(ex.getMessage());
    }
}