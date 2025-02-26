package app.jabafood.cleanarch.domain.exceptions;

public class EmailFormatException extends RuntimeException {
    public EmailFormatException(String message) {
        super(message);
    }
}
