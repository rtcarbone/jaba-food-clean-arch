package app.jabafood.cleanarch.interfaceAdapters.controllers.menuItem;

import app.jabafood.cleanarch.application.useCases.menuItem.ListMenuItemsByRestaurantUseCase;
import app.jabafood.cleanarch.interfaceAdapters.dto.MenuItemResponseDTO;
import app.jabafood.cleanarch.interfaceAdapters.mappers.MenuItemMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/menu-items/list/restaurant")
public class ListMenuItemsByRestaurantController {

    private final ListMenuItemsByRestaurantUseCase listMenuItemsByRestaurantUseCase;
    private final MenuItemMapper menuItemMapper;

    public ListMenuItemsByRestaurantController(ListMenuItemsByRestaurantUseCase listMenuItemsByRestaurantUseCase, MenuItemMapper menuItemMapper) {
        this.listMenuItemsByRestaurantUseCase = listMenuItemsByRestaurantUseCase;
        this.menuItemMapper = menuItemMapper;
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<List<MenuItemResponseDTO>> findByRestaurant(@PathVariable UUID restaurantId) {
        return ResponseEntity.ok(menuItemMapper.toDTOList(listMenuItemsByRestaurantUseCase.execute(restaurantId)));
    }

}
