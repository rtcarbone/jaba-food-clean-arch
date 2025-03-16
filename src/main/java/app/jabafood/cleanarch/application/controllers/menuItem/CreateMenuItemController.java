package app.jabafood.cleanarch.application.controllers.menuItem;

import app.jabafood.cleanarch.application.dto.MenuItemRequestDTO;
import app.jabafood.cleanarch.application.dto.MenuItemResponseDTO;
import app.jabafood.cleanarch.application.mappers.MenuItemMapper;
import app.jabafood.cleanarch.domain.useCases.menuItem.CreateMenuItemUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/v1/menu-items/create")
@RequiredArgsConstructor
@Tag(name = "Menu Item", description = "Menu Item Management API")
public class CreateMenuItemController {
    private final CreateMenuItemUseCase createMenuItemUseCase;
    private final MenuItemMapper menuItemMapper;

    @Operation(summary = "Create a new menu item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Menu item created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PostMapping
    public ResponseEntity<MenuItemResponseDTO> create(@Valid @RequestBody MenuItemRequestDTO menuItemRequestDTO) {
        var menuItem = menuItemMapper.toDomain(menuItemRequestDTO);
        var createdMenuItem = createMenuItemUseCase.execute(menuItem);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/menu-items/{id}")
                .buildAndExpand(createdMenuItem.getId())
                .toUri();

        return ResponseEntity.created(location)
                .body(menuItemMapper.toDTO(createdMenuItem));
    }
}
