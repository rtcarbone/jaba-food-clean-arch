package app.jabafood.cleanarch.application.controllers.menuItem;

import app.jabafood.cleanarch.application.dto.MenuItemResponseDTO;
import app.jabafood.cleanarch.application.mappers.MenuItemMapper;
import app.jabafood.cleanarch.domain.useCases.menuItem.ListMenuItemUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/menu-items/list")
@RequiredArgsConstructor
@Tag(name = "Menu Item", description = "Menu Item Management API")
public class ListMenuItemController {
    private final ListMenuItemUseCase listMenuItemUseCase;
    private final MenuItemMapper menuItemMapper;

    @Operation(summary = "List all menu items")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of menu items retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<MenuItemResponseDTO>> list() {
        return ResponseEntity.ok(menuItemMapper.toDTOList(listMenuItemUseCase.execute()));
    }
}
