package app.jabafood.cleanarch.application.controllers.menuItem;

import app.jabafood.cleanarch.application.dto.MenuItemRequestDTO;
import app.jabafood.cleanarch.application.dto.MenuItemResponseDTO;
import app.jabafood.cleanarch.application.mappers.MenuItemMapper;
import app.jabafood.cleanarch.domain.useCases.menuItem.UpdateMenuItemUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/menu-items/{id}/update")
@RequiredArgsConstructor
@Tag(name = "Menu Item", description = "Menu Item Management API")
public class UpdateMenuItemController {
    private final UpdateMenuItemUseCase updateMenuItemUseCase;
    private final MenuItemMapper menuItemMapper;

    @Operation(summary = "Update menu item details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Menu item updated successfully"),
            @ApiResponse(responseCode = "404", description = "Menu item not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PutMapping
    public ResponseEntity<MenuItemResponseDTO> update(@PathVariable UUID id, @Valid @RequestBody MenuItemRequestDTO menuItemRequestDTO) {
        var menuItem = menuItemMapper.toDomain(menuItemRequestDTO);
        var updatedMenuItem = updateMenuItemUseCase.execute(id, menuItem);
        return ResponseEntity.ok(menuItemMapper.toDTO(updatedMenuItem));
    }
}
