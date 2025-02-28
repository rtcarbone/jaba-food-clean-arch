package app.jabafood.cleanarch.interfaceAdapters.controllers.restaurant;

import app.jabafood.cleanarch.application.useCases.restaurant.UpdateRestaurantUseCase;
import app.jabafood.cleanarch.interfaceAdapters.dto.RestaurantRequestDTO;
import app.jabafood.cleanarch.interfaceAdapters.dto.RestaurantResponseDTO;
import app.jabafood.cleanarch.interfaceAdapters.mappers.RestaurantMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/restaurants/update")
public class UpdateRestaurantController {
    private final UpdateRestaurantUseCase updateRestaurantUseCase;
    private final RestaurantMapper restaurantMapper;

    public UpdateRestaurantController(
            UpdateRestaurantUseCase updateRestaurantUseCase,
            RestaurantMapper restaurantMapper) {
        this.updateRestaurantUseCase = updateRestaurantUseCase;
        this.restaurantMapper = restaurantMapper;
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantResponseDTO> update(@PathVariable UUID id, @RequestBody RestaurantRequestDTO restaurantRequestDTO) {
        var restaurant = restaurantMapper.toDomain(restaurantRequestDTO);
        var updatedRestaurant = updateRestaurantUseCase.execute(id, restaurant);
        return ResponseEntity.ok(restaurantMapper.toDTO(updatedRestaurant));
    }

}
