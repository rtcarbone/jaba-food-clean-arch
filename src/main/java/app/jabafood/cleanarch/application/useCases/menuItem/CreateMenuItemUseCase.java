package app.jabafood.cleanarch.application.useCases.menuItem;

import app.jabafood.cleanarch.domain.entities.MenuItem;
import app.jabafood.cleanarch.domain.gateways.IMenuItemGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateMenuItemUseCase {
    private final IMenuItemGateway menuItemGateway;

    public MenuItem execute(MenuItem menuItem) {
        return menuItemGateway.save(menuItem);
    }
}