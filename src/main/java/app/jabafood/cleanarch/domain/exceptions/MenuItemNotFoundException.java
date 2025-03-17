package app.jabafood.cleanarch.domain.exceptions;

import java.util.UUID;

public class MenuItemNotFoundException extends RuntimeException {
    public MenuItemNotFoundException(UUID id) {
        super("Menu with ID '" + id + "' not found.");
    }
}