package app.jabafood.cleanarch.interfaceAdapters.controllers.menuItem;

import app.jabafood.cleanarch.application.useCases.menuItem.UpdateMenuItemUseCase;
import app.jabafood.cleanarch.interfaceAdapters.dto.MenuItemRequestDTO;
import app.jabafood.cleanarch.interfaceAdapters.dto.MenuItemResponseDTO;
import app.jabafood.cleanarch.interfaceAdapters.mappers.MenuItemMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/menu-items/{id}/update")
@RequiredArgsConstructor
public class UpdateMenuItemController {
    private final UpdateMenuItemUseCase updateMenuItemUseCase;
    private final MenuItemMapper menuItemMapper;

    @PutMapping
    public ResponseEntity<MenuItemResponseDTO> update(@PathVariable UUID id, @Valid @RequestBody MenuItemRequestDTO menuItemRequestDTO) {
        var menuItem = menuItemMapper.toDomain(menuItemRequestDTO);
        var updatedMenuItem = updateMenuItemUseCase.execute(id, menuItem);
        return ResponseEntity.ok(menuItemMapper.toDTO(updatedMenuItem));
    }
}
