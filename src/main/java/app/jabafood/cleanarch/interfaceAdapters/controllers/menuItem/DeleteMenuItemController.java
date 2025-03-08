package app.jabafood.cleanarch.interfaceAdapters.controllers.menuItem;

import app.jabafood.cleanarch.application.useCases.menuItem.DeleteMenuItemUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/menu-items/delete")
public class DeleteMenuItemController {

    private final DeleteMenuItemUseCase deleteMenuItemUseCase;

    public DeleteMenuItemController(DeleteMenuItemUseCase deleteMenuItemUseCase) {
        this.deleteMenuItemUseCase = deleteMenuItemUseCase;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteMenuItemUseCase.execute(id);
        return ResponseEntity.noContent()
                .build();
    }

}
