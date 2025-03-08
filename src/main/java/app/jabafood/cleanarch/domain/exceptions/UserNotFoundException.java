package app.jabafood.cleanarch.domain.exceptions;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(UUID id) {
        super("User with ID '" + id + "' not found.");
    }
}
