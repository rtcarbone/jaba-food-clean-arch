package app.jabafood.cleanarch.domain.exceptions;

public class InvalidClosingTimeException extends RuntimeException {
    public InvalidClosingTimeException() {
        super("Closing time must be later than opening time.");
    }
}
