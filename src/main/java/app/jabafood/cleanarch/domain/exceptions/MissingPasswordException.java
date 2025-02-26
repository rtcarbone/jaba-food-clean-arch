package app.jabafood.cleanarch.domain.exceptions;

public class MissingPasswordException extends RuntimeException {
    public MissingPasswordException(String message) {
        super(message);
    }
}
