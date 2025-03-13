package app.jabafood.cleanarch.application.controllers.menuItem;

import app.jabafood.cleanarch.domain.useCases.menuItem.GetMenuItemByIdUseCase;
import app.jabafood.cleanarch.application.dto.MenuItemResponseDTO;
import app.jabafood.cleanarch.application.mappers.MenuItemMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/menu-items/{id}")
@RequiredArgsConstructor
@Tag(name = "Menu Item", description = "Menu Item Management API")
public class GetMenuItemByIdController {
    private final GetMenuItemByIdUseCase getMenuItemByIdUseCase;
    private final MenuItemMapper menuItemMapper;

    @Operation(summary = "Get menu item by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Menu item found successfully"),
            @ApiResponse(responseCode = "404", description = "Menu item not found")
    })
    @GetMapping
    public ResponseEntity<MenuItemResponseDTO> findById(@PathVariable UUID id) {
        var menuItem = getMenuItemByIdUseCase.execute(id);
        return ResponseEntity.ok(menuItemMapper.toDTO(menuItem));
    }
}
