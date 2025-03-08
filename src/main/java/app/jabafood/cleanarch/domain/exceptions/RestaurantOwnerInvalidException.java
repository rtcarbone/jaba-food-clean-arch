package app.jabafood.cleanarch.domain.exceptions;

import java.util.UUID;

public class RestaurantOwnerInvalidException extends RuntimeException {
    public RestaurantOwnerInvalidException(UUID ownerId) {
        super("User with ID '" + ownerId + "' is not a valid owner for this restaurant.");
    }
}
