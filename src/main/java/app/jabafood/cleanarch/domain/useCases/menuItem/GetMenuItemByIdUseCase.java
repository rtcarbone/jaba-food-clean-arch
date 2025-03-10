package app.jabafood.cleanarch.domain.useCases.menuItem;

import app.jabafood.cleanarch.domain.entities.MenuItem;
import app.jabafood.cleanarch.domain.exceptions.MenuItemNotFoundException;
import app.jabafood.cleanarch.domain.gateways.IMenuItemGateway;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class GetMenuItemByIdUseCase {
    private final IMenuItemGateway menuItemGateway;

    public MenuItem execute(UUID id) {
        return menuItemGateway.findById(id)
                .orElseThrow(() -> new MenuItemNotFoundException(id));
    }
}
