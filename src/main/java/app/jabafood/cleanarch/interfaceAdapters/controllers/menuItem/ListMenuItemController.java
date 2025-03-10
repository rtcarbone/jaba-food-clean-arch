package app.jabafood.cleanarch.interfaceAdapters.controllers.menuItem;

import app.jabafood.cleanarch.application.useCases.menuItem.ListMenuItemUseCase;
import app.jabafood.cleanarch.interfaceAdapters.dto.MenuItemRequestDTO;
import app.jabafood.cleanarch.interfaceAdapters.dto.MenuItemResponseDTO;
import app.jabafood.cleanarch.interfaceAdapters.mappers.MenuItemMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/menu-items/list")
public class ListMenuItemController {

    private final ListMenuItemUseCase listMenuItemUseCase;
    private final MenuItemMapper menuItemMapper;

    public ListMenuItemController(ListMenuItemUseCase listMenuItemUseCase, MenuItemMapper menuItemMapper) {
        this.listMenuItemUseCase = listMenuItemUseCase;
        this.menuItemMapper = menuItemMapper;
    }

    @GetMapping
    public ResponseEntity<List<MenuItemResponseDTO>> list() {
        return ResponseEntity.ok(menuItemMapper.toDTOList(listMenuItemUseCase.execute()));
    }
}
