package app.jabafood.cleanarch.domain.useCases.menuItem;

import app.jabafood.cleanarch.domain.entities.MenuItem;
import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.domain.exceptions.RestaurantNotFoundException;
import app.jabafood.cleanarch.domain.gateways.IMenuItemGateway;
import app.jabafood.cleanarch.domain.gateways.IRestaurantGateway;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class CreateMenuItemUseCase {
    private final IMenuItemGateway menuItemGateway;
    private final IRestaurantGateway restaurantGateway;

    public MenuItem execute(MenuItem menuItem) {

        menuItem.validate();

        Optional<Restaurant> existingRestaurant = restaurantGateway.findById(menuItem.getRestaurant()
                                                                                     .getId());
        if (existingRestaurant.isEmpty()) {
            throw new RestaurantNotFoundException(menuItem.getRestaurant()
                                                          .getId());
        }

        MenuItem newMenuItem = menuItem.copyWith(
                menuItem.getName(),
                menuItem.getDescription(),
                menuItem.getPrice(),
                menuItem.getInRestaurantOnly(),
                menuItem.getImagePath(),
                existingRestaurant.get()
        );

        return menuItemGateway.save(newMenuItem);
    }
}