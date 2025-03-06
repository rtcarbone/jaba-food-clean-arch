package app.jabafood.cleanarch.application.useCases.menuItem;

import app.jabafood.cleanarch.domain.entities.MenuItem;
import app.jabafood.cleanarch.domain.gateways.IMenuItemGateway;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class ListMenuItemsByRestaurantUseCase {
    private final IMenuItemGateway menuItemGateway;

    public List<MenuItem> execute(UUID restaurantId) {
        // Business logic goes here
        return menuItemGateway.findByRestaurantId(restaurantId);
    }
}