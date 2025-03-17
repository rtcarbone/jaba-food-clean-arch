package app.jabafood.cleanarch.application.controllers.restaurant;

import app.jabafood.cleanarch.domain.useCases.restaurant.DeleteRestaurantUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/restaurants/{id}/delete")
@RequiredArgsConstructor
@Tag(name = "Restaurant", description = "Restaurant Management API")
public class DeleteRestaurantController {
    private final DeleteRestaurantUseCase deleteRestaurantUseCase;

    @Operation(summary = "Delete a restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Restaurant deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteRestaurantUseCase.execute(id);
        return ResponseEntity.noContent()
                .build();
    }
}
