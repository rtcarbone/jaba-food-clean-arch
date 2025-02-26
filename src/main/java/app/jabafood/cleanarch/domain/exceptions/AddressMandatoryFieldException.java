package app.jabafood.cleanarch.domain.exceptions;

public class AddressMandatoryFieldException extends RuntimeException {
    public AddressMandatoryFieldException(String message) {
        super(message);
    }
}
