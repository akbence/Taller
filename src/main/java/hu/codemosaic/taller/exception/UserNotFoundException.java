package hu.codemosaic.taller.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BaseException {
    public UserNotFoundException(String message, HttpStatus status) {
        super(message, status);
    }
}
