package app.jabafood.cleanarch.application.useCases.menuItem;

import app.jabafood.cleanarch.domain.gateways.IMenuItemGateway;

import java.util.UUID;

public class DeleteMenuItemUseCase {
    private final IMenuItemGateway menuItemGateway;

    public DeleteMenuItemUseCase(IMenuItemGateway menuItemGateway) {
        this.menuItemGateway = menuItemGateway;
    }

    public void execute(UUID id) {
        if (menuItemGateway.findById(id)
                .isEmpty()) {
            throw new RuntimeException("Menu item not found");
        }
        menuItemGateway.delete(id);
    }
}
