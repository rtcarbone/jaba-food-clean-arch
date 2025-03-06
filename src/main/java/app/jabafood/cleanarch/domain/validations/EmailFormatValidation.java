package app.jabafood.cleanarch.domain.validations;

import app.jabafood.cleanarch.domain.exceptions.EmailFormatException;

public class EmailFormatValidation {
    private static final String EMAIL_PATTERN =
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

    public static void validate(String email) {
        if (email == null || !email.matches(EMAIL_PATTERN)) {
            throw new EmailFormatException("Invalid email format");
        }
    }
}
