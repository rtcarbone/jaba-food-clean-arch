package app.jabafood.cleanarch.domain.exceptions;

public class InvalidPageValueException extends RuntimeException {
    public InvalidPageValueException(String message) {
        super(message);
    }
}
