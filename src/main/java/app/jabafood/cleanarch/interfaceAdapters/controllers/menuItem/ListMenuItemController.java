package app.jabafood.cleanarch.interfaceAdapters.controllers.menuItem;

import app.jabafood.cleanarch.application.useCases.menuItem.ListMenuItemUseCase;
import app.jabafood.cleanarch.interfaceAdapters.dto.MenuItemResponseDTO;
import app.jabafood.cleanarch.interfaceAdapters.mappers.MenuItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/menu-items/list")
@RequiredArgsConstructor
public class ListMenuItemController {
    private final ListMenuItemUseCase listMenuItemUseCase;
    private final MenuItemMapper menuItemMapper;

    @GetMapping
    public ResponseEntity<List<MenuItemResponseDTO>> list() {
        return ResponseEntity.ok(menuItemMapper.toDTOList(listMenuItemUseCase.execute()));
    }
}
