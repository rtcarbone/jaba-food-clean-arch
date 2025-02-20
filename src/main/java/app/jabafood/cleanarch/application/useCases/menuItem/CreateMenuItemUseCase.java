package app.jabafood.cleanarch.application.useCases.menuItem;

import app.jabafood.cleanarch.domain.entities.MenuItem;
import app.jabafood.cleanarch.domain.repositories.MenuItemRepository;

public class CreateMenuItemUseCase {
    private final MenuItemRepository menuItemRepository;

    public CreateMenuItemUseCase(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public MenuItem execute(MenuItem menuItem) {
        return menuItemRepository.save(menuItem);
    }
}