package app.jabafood.cleanarch.domain.exceptions;

import java.util.UUID;

public class RestaurantNotFoundException extends RuntimeException {
    public RestaurantNotFoundException(UUID id) {
        super("Restaurant with ID '" + id + "' not found.");
    }
}