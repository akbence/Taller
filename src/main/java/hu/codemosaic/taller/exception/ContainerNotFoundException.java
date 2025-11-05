package hu.codemosaic.taller.exception;

import org.springframework.http.HttpStatus;

public class ContainerNotFoundException extends BaseException {
    public ContainerNotFoundException() {
        super("Container not found for the user!", HttpStatus.NOT_FOUND);
    }
}
