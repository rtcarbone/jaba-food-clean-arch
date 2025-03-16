package app.jabafood.cleanarch.domain.exceptions;

public class InvalidEnumException extends RuntimeException {
    public InvalidEnumException(String message) {
        super("Invalid enum value: " + message);
    }
}

