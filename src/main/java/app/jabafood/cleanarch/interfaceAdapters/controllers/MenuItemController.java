package app.jabafood.cleanarch.interfaceAdapters.controllers;

import app.jabafood.cleanarch.application.useCases.menuItem.*;
import app.jabafood.cleanarch.interfaceAdapters.dto.MenuItemRequestDTO;
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
    public ResponseEntity<MenuItemRequestDTO> create(@Valid @RequestBody MenuItemRequestDTO menuItemRequestDTO) {
        var menuItem = menuItemMapper.toDomain(menuItemRequestDTO);
        var createdMenuItem = createMenuItemUseCase.execute(menuItem);
        return ResponseEntity.ok(menuItemMapper.toDTO(createdMenuItem));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItemRequestDTO> findById(@PathVariable UUID id) {
        var menuItem = getMenuItemByIdUseCase.execute(id);
        return ResponseEntity.ok(menuItemMapper.toDTO(menuItem));
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<MenuItemRequestDTO>> findByRestaurant(@PathVariable UUID restaurantId) {
        return ResponseEntity.ok(menuItemMapper.toDTOList(listMenuItemsByRestaurantUseCase.execute(restaurantId)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuItemRequestDTO> update(@PathVariable UUID id, @Valid @RequestBody MenuItemRequestDTO menuItemRequestDTO) {
        var menuItem = menuItemMapper.toDomain(menuItemRequestDTO);
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
