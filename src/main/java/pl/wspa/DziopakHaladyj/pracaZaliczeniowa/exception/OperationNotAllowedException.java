package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class OperationNotAllowedException extends ResponseStatusException {
    public OperationNotAllowedException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
