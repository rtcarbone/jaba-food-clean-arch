package app.jabafood.cleanarch.application.controllers.menuItem;

import app.jabafood.cleanarch.domain.useCases.menuItem.ListMenuItemsByRestaurantUseCase;
import app.jabafood.cleanarch.application.dto.MenuItemResponseDTO;
import app.jabafood.cleanarch.application.mappers.MenuItemMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Tag(name = "List Menu Items By Restaurant Owner", description = "List Menu Items By Restaurant Owner API")
public class ListMenuItemsByRestaurantController {
    private final ListMenuItemsByRestaurantUseCase listMenuItemsByRestaurantUseCase;
    private final MenuItemMapper menuItemMapper;

    @GetMapping
    public ResponseEntity<List<MenuItemResponseDTO>> findByRestaurant(@PathVariable UUID restaurantId) {
        return ResponseEntity.ok(menuItemMapper.toDTOList(listMenuItemsByRestaurantUseCase.execute(restaurantId)));
    }
}