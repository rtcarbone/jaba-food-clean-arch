package app.jabafood.cleanarch.domain.useCases.menuItem;

import app.jabafood.cleanarch.domain.exceptions.MenuItemNotFoundException;
import app.jabafood.cleanarch.domain.gateways.IMenuItemGateway;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class DeleteMenuItemUseCase {
    private final IMenuItemGateway menuItemGateway;

    public void execute(UUID id) {
        if (menuItemGateway.findById(id)
                .isEmpty()) {
            throw new MenuItemNotFoundException(id);
        }
        menuItemGateway.delete(id);
    }
}
