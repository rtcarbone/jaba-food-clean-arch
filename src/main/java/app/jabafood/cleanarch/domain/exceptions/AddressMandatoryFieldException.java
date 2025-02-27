package app.jabafood.cleanarch.domain.exceptions;

public class AddressMandatoryFieldException extends RuntimeException {
    public AddressMandatoryFieldException(String field) {
        super("The field '" + field + "' is mandatory for address registration.");
    }
}
