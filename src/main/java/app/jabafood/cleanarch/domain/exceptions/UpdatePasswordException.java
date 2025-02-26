package app.jabafood.cleanarch.domain.exceptions;

public class UpdatePasswordException extends RuntimeException {
    public UpdatePasswordException(String message) {
        super(message);
    }
}
