package app.jabafood.cleanarch.interfaceAdapters.controllers.menuItem;

import app.jabafood.cleanarch.application.useCases.menuItem.CreateMenuItemUseCase;
import app.jabafood.cleanarch.interfaceAdapters.dto.MenuItemRequestDTO;
import app.jabafood.cleanarch.interfaceAdapters.dto.MenuItemResponseDTO;
import app.jabafood.cleanarch.interfaceAdapters.mappers.MenuItemMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/menu-items/create")
@RequiredArgsConstructor
public class CreateMenuItemController {
    private final CreateMenuItemUseCase createMenuItemUseCase;
    private final MenuItemMapper menuItemMapper;

    @PostMapping
    public ResponseEntity<MenuItemResponseDTO> create(@Valid @RequestBody MenuItemRequestDTO menuItemRequestDTO) {
        var menuItem = menuItemMapper.toDomain(menuItemRequestDTO);
        var createdMenuItem = createMenuItemUseCase.execute(menuItem);
        return ResponseEntity.ok(menuItemMapper.toDTO(createdMenuItem));
    }
}
