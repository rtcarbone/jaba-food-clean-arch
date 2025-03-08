package app.jabafood.cleanarch.application.useCases.menuItem;

import app.jabafood.cleanarch.domain.entities.MenuItem;
import app.jabafood.cleanarch.domain.exceptions.MenuItemNotFoundException;
import app.jabafood.cleanarch.domain.gateways.IMenuItemGateway;

import java.util.UUID;

public class UpdateMenuItemUseCase {
    private final IMenuItemGateway menuItemGateway;

    public UpdateMenuItemUseCase(IMenuItemGateway menuItemGateway) {
        this.menuItemGateway = menuItemGateway;
    }

    public MenuItem execute(UUID id, MenuItem updatedData) {
        MenuItem existingItem = menuItemGateway.findById(id)
                .orElseThrow(() -> new MenuItemNotFoundException(id));

        MenuItem updatedItem = existingItem.copyWith(
                updatedData.getName(),
                updatedData.getDescription(),
                updatedData.getPrice(),
                updatedData.getInRestaurantOnly(),
                updatedData.getImagePath(),
                existingItem.getRestaurant()
        );

        updatedItem.validate();

        return menuItemGateway.save(updatedItem);
    }
}