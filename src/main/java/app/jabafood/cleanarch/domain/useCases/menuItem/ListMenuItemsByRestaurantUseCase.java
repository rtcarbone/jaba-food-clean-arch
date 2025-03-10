package app.jabafood.cleanarch.domain.useCases.menuItem;

import app.jabafood.cleanarch.domain.entities.MenuItem;
import app.jabafood.cleanarch.domain.exceptions.RestaurantNotFoundException;
import app.jabafood.cleanarch.domain.gateways.IMenuItemGateway;
import app.jabafood.cleanarch.domain.gateways.IRestaurantGateway;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class ListMenuItemsByRestaurantUseCase {
    private final IMenuItemGateway menuItemGateway;
    private final IRestaurantGateway restaurantGateway;

    public List<MenuItem> execute(UUID restaurantId) {
        if (restaurantGateway.findById(restaurantId)
                .isEmpty()) {
            throw new RestaurantNotFoundException(restaurantId);
        }
        return menuItemGateway.findByRestaurantId(restaurantId);
    }
}