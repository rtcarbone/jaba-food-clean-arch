package app.jabafood.cleanarch.domain.exceptions;

public class MenuItemMandatoryFieldException extends RuntimeException {
    public MenuItemMandatoryFieldException(String field) {
        super("The field '" + field + "' is mandatory for menu registration.");
    }
}
