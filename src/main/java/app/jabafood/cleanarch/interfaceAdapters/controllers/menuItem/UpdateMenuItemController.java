package app.jabafood.cleanarch.interfaceAdapters.controllers.menuItem;

import app.jabafood.cleanarch.application.useCases.menuItem.UpdateMenuItemUseCase;
import app.jabafood.cleanarch.interfaceAdapters.dto.MenuItemRequestDTO;
import app.jabafood.cleanarch.interfaceAdapters.mappers.MenuItemMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/menu-items/update")
public class UpdateMenuItemController {

    private final UpdateMenuItemUseCase updateMenuItemUseCase;
    private final MenuItemMapper menuItemMapper;

    public UpdateMenuItemController(UpdateMenuItemUseCase updateMenuItemUseCase, MenuItemMapper menuItemMapper) {
        this.updateMenuItemUseCase = updateMenuItemUseCase;
        this.menuItemMapper = menuItemMapper;
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuItemRequestDTO> update(@PathVariable UUID id, @Valid @RequestBody MenuItemRequestDTO menuItemRequestDTO) {
        var menuItem = menuItemMapper.toDomain(menuItemRequestDTO);
        var updatedMenuItem = updateMenuItemUseCase.execute(id, menuItem);
        return ResponseEntity.ok(menuItemMapper.toDTO(updatedMenuItem));
    }

}
