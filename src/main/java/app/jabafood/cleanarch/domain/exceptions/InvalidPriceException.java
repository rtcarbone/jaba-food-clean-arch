package app.jabafood.cleanarch.domain.exceptions;

public class InvalidPriceException extends RuntimeException {
    public InvalidPriceException() {
        super("Price must be a value greater than or equal to 0.");
    }
}
