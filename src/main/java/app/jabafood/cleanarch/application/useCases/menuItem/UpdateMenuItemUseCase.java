package app.jabafood.cleanarch.application.useCases.menuItem;

import app.jabafood.cleanarch.domain.entities.MenuItem;
import app.jabafood.cleanarch.domain.gateways.IMenuItemGateway;

import java.util.UUID;

public class UpdateMenuItemUseCase {
    private final IMenuItemGateway menuItemGateway;

    public UpdateMenuItemUseCase(IMenuItemGateway menuItemGateway) {
        this.menuItemGateway = menuItemGateway;
    }

    public MenuItem execute(UUID id, MenuItem updatedData) {
        MenuItem existingItem = menuItemGateway.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));

        MenuItem updatedItem = new MenuItem(
                existingItem.getId(),
                updatedData.getName() != null ? updatedData.getName() : existingItem.getName(),
                updatedData.getDescription() != null ? updatedData.getDescription() : existingItem.getDescription(),
                updatedData.getPrice() != null ? updatedData.getPrice() : existingItem.getPrice(),
                updatedData.isInRestaurantOnly(),
                updatedData.getImagePath() != null ? updatedData.getImagePath() : existingItem.getImagePath(),
                existingItem.getRestaurantId()
        );

        return menuItemGateway.save(updatedItem);
    }
}