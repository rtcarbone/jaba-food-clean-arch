package app.jabafood.cleanarch.application.useCases.menuItem;

import app.jabafood.cleanarch.domain.entities.MenuItem;
import app.jabafood.cleanarch.domain.repositories.MenuItemRepository;

import java.util.List;
import java.util.UUID;

public class ListMenuItemsByRestaurantUseCase {
    private final MenuItemRepository menuItemRepository;

    public ListMenuItemsByRestaurantUseCase(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public List<MenuItem> execute(UUID restaurantId) {
        return menuItemRepository.findByRestaurantId(restaurantId);
    }
}