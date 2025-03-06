package app.jabafood.cleanarch.interfaceAdapters.controllers.restaurant;

import app.jabafood.cleanarch.application.useCases.restaurant.DeleteRestaurantUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/restaurants/{id}/delete")
public class DeleteRestaurantController {

    private final DeleteRestaurantUseCase deleteRestaurantUseCase;

    public DeleteRestaurantController(DeleteRestaurantUseCase deleteRestaurantUseCase) {
        this.deleteRestaurantUseCase = deleteRestaurantUseCase;
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteRestaurantUseCase.execute(id);
        return ResponseEntity.noContent()
                .build();
    }

}
