package app.jabafood.cleanarch.application.controllers.menuItem;

import app.jabafood.cleanarch.application.dto.MenuItemResponseDTO;
import app.jabafood.cleanarch.application.mappers.MenuItemMapper;
import app.jabafood.cleanarch.domain.useCases.menuItem.ListMenuItemsByRestaurantUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/menu-items/list/restaurant/{restaurantId}")
@RequiredArgsConstructor
@Tag(name = "Menu Item", description = "Menu Item Management API")
public class ListMenuItemsByRestaurantController {
    private final ListMenuItemsByRestaurantUseCase listMenuItemsByRestaurantUseCase;
    private final MenuItemMapper menuItemMapper;

    @Operation(summary = "List all menu items for a specific restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Menu items retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    @GetMapping
    public ResponseEntity<List<MenuItemResponseDTO>> findByRestaurant(@PathVariable UUID restaurantId) {
        return ResponseEntity.ok(menuItemMapper.toDTOList(listMenuItemsByRestaurantUseCase.execute(restaurantId)));
    }
}