package app.jabafood.cleanarch.interfaceAdapters.controllers.menuItem;

import app.jabafood.cleanarch.application.useCases.menuItem.GetMenuItemByIdUseCase;
import app.jabafood.cleanarch.interfaceAdapters.dto.MenuItemRequestDTO;
import app.jabafood.cleanarch.interfaceAdapters.mappers.MenuItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/menu-items")
@RequiredArgsConstructor
public class GetMenuItemByIdController {
    private final GetMenuItemByIdUseCase getMenuItemByIdUseCase;
    private final MenuItemMapper menuItemMapper;

    @GetMapping("/{id}")
    public ResponseEntity<MenuItemRequestDTO> findById(@PathVariable UUID id) {
        var menuItem = getMenuItemByIdUseCase.execute(id);
        return ResponseEntity.ok(menuItemMapper.toDTO(menuItem));
    }
}
