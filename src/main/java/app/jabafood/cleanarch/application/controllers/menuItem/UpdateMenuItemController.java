package app.jabafood.cleanarch.application.controllers.menuItem;

import app.jabafood.cleanarch.domain.useCases.menuItem.UpdateMenuItemUseCase;
import app.jabafood.cleanarch.application.dto.MenuItemRequestDTO;
import app.jabafood.cleanarch.application.dto.MenuItemResponseDTO;
import app.jabafood.cleanarch.application.mappers.MenuItemMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/menu-items/{id}/update")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Update Menu Item", description = "Update Menu Item API")
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
