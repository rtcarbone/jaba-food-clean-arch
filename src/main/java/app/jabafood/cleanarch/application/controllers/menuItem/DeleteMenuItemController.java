package app.jabafood.cleanarch.application.controllers.menuItem;

import app.jabafood.cleanarch.domain.useCases.menuItem.DeleteMenuItemUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/menu-items/{id}/delete")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Delete Menu Item", description = "Delete Menu Item API")
public class DeleteMenuItemController {
    private final DeleteMenuItemUseCase deleteMenuItemUseCase;

    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteMenuItemUseCase.execute(id);
        return ResponseEntity.noContent()
                .build();
    }
}
