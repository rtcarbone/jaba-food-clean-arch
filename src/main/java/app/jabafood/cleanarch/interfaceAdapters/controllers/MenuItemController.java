package app.jabafood.cleanarch.interfaceAdapters.controllers;

import app.jabafood.cleanarch.application.useCases.menuItem.*;
import app.jabafood.cleanarch.interfaceAdapters.dto.MenuItemDTO;
import app.jabafood.cleanarch.interfaceAdapters.mappers.MenuItemMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/menu-items")
public class MenuItemController {

    private final CreateMenuItemUseCase createMenuItemUseCase;
    private final GetMenuItemByIdUseCase getMenuItemByIdUseCase;
    private final UpdateMenuItemUseCase updateMenuItemUseCase;
    private final DeleteMenuItemUseCase deleteMenuItemUseCase;
    private final ListMenuItemsByRestaurantUseCase listMenuItemsByRestaurantUseCase;
    private final MenuItemMapper menuItemMapper;

    public MenuItemController(CreateMenuItemUseCase createMenuItemUseCase,
                              GetMenuItemByIdUseCase getMenuItemByIdUseCase,
                              UpdateMenuItemUseCase updateMenuItemUseCase,
                              DeleteMenuItemUseCase deleteMenuItemUseCase,
                              ListMenuItemsByRestaurantUseCase listMenuItemsByRestaurantUseCase,
                              MenuItemMapper menuItemMapper) {
        this.createMenuItemUseCase = createMenuItemUseCase;
        this.getMenuItemByIdUseCase = getMenuItemByIdUseCase;
        this.updateMenuItemUseCase = updateMenuItemUseCase;
        this.deleteMenuItemUseCase = deleteMenuItemUseCase;
        this.listMenuItemsByRestaurantUseCase = listMenuItemsByRestaurantUseCase;
        this.menuItemMapper = menuItemMapper;
    }

    @PostMapping
    public ResponseEntity<MenuItemDTO> create(@Valid @RequestBody MenuItemDTO menuItemDTO) {
        var menuItem = menuItemMapper.toEntity(menuItemDTO);
        var createdMenuItem = createMenuItemUseCase.execute(menuItem);
        return ResponseEntity.ok(menuItemMapper.toDTO(createdMenuItem));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItemDTO> findById(@PathVariable UUID id) {
        var menuItem = getMenuItemByIdUseCase.execute(id);
        return ResponseEntity.ok(menuItemMapper.toDTO(menuItem));
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<MenuItemDTO>> findByRestaurant(@PathVariable UUID restaurantId) {
        return ResponseEntity.ok(menuItemMapper.toDTOList(listMenuItemsByRestaurantUseCase.execute(restaurantId)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuItemDTO> update(@PathVariable UUID id, @Valid @RequestBody MenuItemDTO menuItemDTO) {
        var menuItem = menuItemMapper.toEntity(menuItemDTO);
        var updatedMenuItem = updateMenuItemUseCase.execute(id, menuItem);
        return ResponseEntity.ok(menuItemMapper.toDTO(updatedMenuItem));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteMenuItemUseCase.execute(id);
        return ResponseEntity.noContent()
                .build();
    }

}
