package app.jabafood.cleanarch.domain.exceptions;

public class UserMandatoryFieldException extends RuntimeException {
    public UserMandatoryFieldException(String message) {
        super(message);
    }
}
