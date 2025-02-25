package app.jabafood.cleanarch.application.useCases.menuItem;

import app.jabafood.cleanarch.domain.entities.MenuItem;
import app.jabafood.cleanarch.domain.gateways.IMenuItemGateway;

import java.util.UUID;

public class GetMenuItemByIdUseCase {
    private final IMenuItemGateway menuItemGateway;

    public GetMenuItemByIdUseCase(IMenuItemGateway menuItemGateway) {
        this.menuItemGateway = menuItemGateway;
    }

    public MenuItem execute(UUID id) {
        return menuItemGateway.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));
    }
}
