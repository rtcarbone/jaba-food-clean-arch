package app.jabafood.cleanarch.application.controllers.menuItem;

import app.jabafood.cleanarch.domain.useCases.menuItem.DeleteMenuItemUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@Tag(name = "Menu Item", description = "Menu Item Management API")
public class DeleteMenuItemController {
    private final DeleteMenuItemUseCase deleteMenuItemUseCase;

    @Operation(summary = "Delete a menu item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Menu item deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Menu item not found")
    })
    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteMenuItemUseCase.execute(id);
        return ResponseEntity.noContent()
                .build();
    }
}
