package app.jabafood.cleanarch.application.useCases.menuItem;


import app.jabafood.cleanarch.domain.entities.MenuItem;
import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.domain.exceptions.MenuItemMandatoryFieldException;
import app.jabafood.cleanarch.domain.exceptions.MenuItemRestaurantInvalidException;
import app.jabafood.cleanarch.domain.gateways.IMenuItemGateway;
import app.jabafood.cleanarch.domain.gateways.IRestaurantGateway;

import java.util.Optional;

public class CreateMenuItemUseCase {
    private final IMenuItemGateway menuItemGateway;
    private final IRestaurantGateway restaurantGateway;

    public CreateMenuItemUseCase(IMenuItemGateway menuItemGateway, IRestaurantGateway restaurantGateway) {
        this.menuItemGateway = menuItemGateway;
        this.restaurantGateway = restaurantGateway;
    }

    public MenuItem execute(MenuItem menuItem) {

        if (menuItem.getRestaurant() == null || menuItem.getRestaurant()
                .getId() == null) {
            throw new MenuItemMandatoryFieldException("Restaurant");
        }

        Optional<Restaurant> existingRestaurant = restaurantGateway.findById(menuItem.getRestaurant()
                .getId());

        if (existingRestaurant.isEmpty()) {
            throw new MenuItemRestaurantInvalidException(menuItem.getRestaurant()
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

        newMenuItem.validate();

        return menuItemGateway.save(newMenuItem);
    }
}
