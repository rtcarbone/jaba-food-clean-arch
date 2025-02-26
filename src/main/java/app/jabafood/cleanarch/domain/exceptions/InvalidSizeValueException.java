package app.jabafood.cleanarch.domain.exceptions;

public class InvalidSizeValueException extends RuntimeException {
    public InvalidSizeValueException(String message) {
        super(message);
    }
}
