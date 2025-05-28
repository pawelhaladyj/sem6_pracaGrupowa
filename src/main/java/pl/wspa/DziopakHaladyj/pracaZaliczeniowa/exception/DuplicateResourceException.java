package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DuplicateResourceException extends ResponseStatusException {
    public DuplicateResourceException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}