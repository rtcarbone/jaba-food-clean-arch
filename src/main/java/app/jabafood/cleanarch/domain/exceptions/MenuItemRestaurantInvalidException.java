package app.jabafood.cleanarch.domain.exceptions;

import java.util.UUID;

public class MenuItemRestaurantInvalidException extends RuntimeException{
    public MenuItemRestaurantInvalidException(UUID restaurant_id) {
        super("Restaurant with ID " + restaurant_id + " is not a valid id for this restaurant.");
    }
}