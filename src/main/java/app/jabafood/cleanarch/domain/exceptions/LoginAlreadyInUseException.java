package app.jabafood.cleanarch.domain.exceptions;

public class LoginAlreadyInUseException extends RuntimeException {
    public LoginAlreadyInUseException(String message) {
        super(message);
    }
}
