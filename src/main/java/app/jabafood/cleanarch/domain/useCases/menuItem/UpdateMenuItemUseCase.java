package app.jabafood.cleanarch.domain.useCases.menuItem;

import app.jabafood.cleanarch.domain.entities.MenuItem;
import app.jabafood.cleanarch.domain.gateways.IMenuItemGateway;
import app.jabafood.cleanarch.domain.exceptions.MenuItemNotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class UpdateMenuItemUseCase {
    private final IMenuItemGateway menuItemGateway;

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