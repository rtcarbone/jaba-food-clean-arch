package app.jabafood.cleanarch.application.useCases.menuItem;

import app.jabafood.cleanarch.domain.repositories.MenuItemRepository;

import java.util.UUID;

public class DeleteMenuItemUseCase {
    private final MenuItemRepository menuItemRepository;

    public DeleteMenuItemUseCase(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public void execute(UUID id) {
        if (!menuItemRepository.findById(id)
                .isPresent()) {
            throw new RuntimeException("Menu item not found");
        }
        menuItemRepository.delete(id);
    }
}
