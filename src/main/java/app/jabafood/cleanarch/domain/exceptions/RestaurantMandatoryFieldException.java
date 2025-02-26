package app.jabafood.cleanarch.domain.exceptions;

public class RestaurantMandatoryFieldException extends RuntimeException {
    public RestaurantMandatoryFieldException(String field) {
        super("The field '" + field + "' is mandatory for restaurant registration.");
    }
}
