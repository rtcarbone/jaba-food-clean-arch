package app.jabafood.cleanarch.interfaceAdapters.controllers.menuItem;

import app.jabafood.cleanarch.application.useCases.menuItem.ListMenuItemsByRestaurantUseCase;
import app.jabafood.cleanarch.interfaceAdapters.dto.MenuItemRequestDTO;
import app.jabafood.cleanarch.interfaceAdapters.mappers.MenuItemMapper;
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
public class ListMenuItemsByRestaurantController {
    private final ListMenuItemsByRestaurantUseCase listMenuItemsByRestaurantUseCase;
    private final MenuItemMapper menuItemMapper;

    @GetMapping
    public ResponseEntity<List<MenuItemRequestDTO>> findByRestaurant(@PathVariable UUID restaurantId) {
        return ResponseEntity.ok(menuItemMapper.toDTOList(listMenuItemsByRestaurantUseCase.execute(restaurantId)));
    }
}