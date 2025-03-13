package app.jabafood.cleanarch.application.controllers.restaurant;

import app.jabafood.cleanarch.domain.useCases.restaurant.DeleteRestaurantUseCase;
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
@RequestMapping("api/v1/restaurants/{id}/delete")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Delete Restaurant", description = "Delete Restaurant API")
public class DeleteRestaurantController {
    private final DeleteRestaurantUseCase deleteRestaurantUseCase;

    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteRestaurantUseCase.execute(id);
        return ResponseEntity.noContent()
                .build();
    }
}
