package app.jabafood.cleanarch.application.controllers.menuItem;

import app.jabafood.cleanarch.domain.useCases.menuItem.ListMenuItemUseCase;
import app.jabafood.cleanarch.application.dto.MenuItemResponseDTO;
import app.jabafood.cleanarch.application.mappers.MenuItemMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/menu-items/list")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "List Menu Items", description = "List Menu Items API")
public class ListMenuItemController {
    private final ListMenuItemUseCase listMenuItemUseCase;
    private final MenuItemMapper menuItemMapper;

    @GetMapping
    public ResponseEntity<List<MenuItemResponseDTO>> list() {
        return ResponseEntity.ok(menuItemMapper.toDTOList(listMenuItemUseCase.execute()));
    }
}
