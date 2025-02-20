package app.jabafood.cleanarch.application.useCases.menuItem;

import app.jabafood.cleanarch.domain.entities.MenuItem;
import app.jabafood.cleanarch.domain.repositories.MenuItemRepository;

import java.util.UUID;

public class UpdateMenuItemUseCase {
    private final MenuItemRepository menuItemRepository;

    public UpdateMenuItemUseCase(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public MenuItem execute(UUID id, MenuItem updatedData) {
        MenuItem existingItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));

        MenuItem updatedItem = new MenuItem(
                existingItem.getId(),
                updatedData.getName() != null ? updatedData.getName() : existingItem.getName(),
                updatedData.getDescription() != null ? updatedData.getDescription() : existingItem.getDescription(),
                updatedData.getPrice() != null ? updatedData.getPrice() : existingItem.getPrice(),
                updatedData.isInRestaurantOnly(),
                updatedData.getImagePath() != null ? updatedData.getImagePath() : existingItem.getImagePath(),
                existingItem.getRestaurant()
        );

        return menuItemRepository.save(updatedItem);
    }
}