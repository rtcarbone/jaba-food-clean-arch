package app.jabafood.cleanarch.application.controllers.menuItem;

import app.jabafood.cleanarch.domain.useCases.menuItem.CreateMenuItemUseCase;
import app.jabafood.cleanarch.application.dto.MenuItemRequestDTO;
import app.jabafood.cleanarch.application.dto.MenuItemResponseDTO;
import app.jabafood.cleanarch.application.mappers.MenuItemMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/menu-items/create")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Create Menu Item", description = "Create Menu Item API")
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
