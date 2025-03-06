package app.jabafood.cleanarch.application.useCases.menuItem;

import app.jabafood.cleanarch.domain.entities.MenuItem;
import app.jabafood.cleanarch.domain.gateways.IMenuItemGateway;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class UpdateMenuItemUseCase {
    private final IMenuItemGateway menuItemGateway;

    public MenuItem execute(UUID id, MenuItem updatedData) {
        MenuItem existingItem = menuItemGateway.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));

        MenuItem updatedItem = existingItem.copyWith(
                updatedData.getName(),
                updatedData.getDescription(),
                updatedData.getPrice(),
                updatedData.isInRestaurantOnly(),
                updatedData.getImagePath(),
                existingItem.getRestaurant()
        );

        return menuItemGateway.save(updatedItem);
    }
}