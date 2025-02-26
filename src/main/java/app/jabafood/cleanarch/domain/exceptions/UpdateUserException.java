package app.jabafood.cleanarch.domain.exceptions;

public class UpdateUserException extends RuntimeException {
    public UpdateUserException(String message) {
        super(message);
    }
}
