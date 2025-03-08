package app.jabafood.cleanarch.application.useCases.menuItem;

import app.jabafood.cleanarch.domain.entities.MenuItem;
import app.jabafood.cleanarch.domain.gateways.IMenuItemGateway;

import java.util.List;

public class ListMenuItemUseCase {
    private final IMenuItemGateway menuItemGateway;

    public ListMenuItemUseCase(IMenuItemGateway menuItemGateway) {
        this.menuItemGateway = menuItemGateway;
    }

    public List<MenuItem> execute() {
        return menuItemGateway.findAll();
    }
}
