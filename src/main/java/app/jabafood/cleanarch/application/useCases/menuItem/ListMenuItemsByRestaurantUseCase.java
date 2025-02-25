package app.jabafood.cleanarch.application.useCases.menuItem;

import app.jabafood.cleanarch.domain.entities.MenuItem;
import app.jabafood.cleanarch.domain.gateways.IMenuItemGateway;

import java.util.List;
import java.util.UUID;

public class ListMenuItemsByRestaurantUseCase {
    private final IMenuItemGateway menuItemGateway;

    public ListMenuItemsByRestaurantUseCase(IMenuItemGateway menuItemGateway) {
        this.menuItemGateway = menuItemGateway;
    }

    public List<MenuItem> execute(UUID restaurantId) {
        // Business logic goes here
        return menuItemGateway.findByRestaurantId(restaurantId);
    }
}