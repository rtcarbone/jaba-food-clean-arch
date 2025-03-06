package app.jabafood.cleanarch.domain.exceptions;

public class UserMandatoryFieldException extends RuntimeException {
    public UserMandatoryFieldException(String field) {
        super("The field '" + field + "' is mandatory for user registration.");
    }
}
