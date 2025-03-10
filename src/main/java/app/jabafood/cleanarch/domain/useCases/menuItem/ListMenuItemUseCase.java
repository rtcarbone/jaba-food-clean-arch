package app.jabafood.cleanarch.domain.useCases.menuItem;

import app.jabafood.cleanarch.domain.entities.MenuItem;
import app.jabafood.cleanarch.domain.gateways.IMenuItemGateway;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ListMenuItemUseCase {
    private final IMenuItemGateway menuItemGateway;

    public List<MenuItem> execute() {
        return menuItemGateway.findAll();
    }
}
