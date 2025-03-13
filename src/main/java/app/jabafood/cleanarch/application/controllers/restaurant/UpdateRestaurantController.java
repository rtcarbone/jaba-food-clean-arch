package app.jabafood.cleanarch.application.controllers.restaurant;

import app.jabafood.cleanarch.domain.useCases.restaurant.UpdateRestaurantUseCase;
import app.jabafood.cleanarch.application.dto.RestaurantRequestDTO;
import app.jabafood.cleanarch.application.dto.RestaurantResponseDTO;
import app.jabafood.cleanarch.application.mappers.RestaurantMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/restaurants/{id}/update")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Update Restaurant", description = "Update Restaurant API")
public class UpdateRestaurantController {
    private final UpdateRestaurantUseCase updateRestaurantUseCase;
    private final RestaurantMapper restaurantMapper;

    @PutMapping
    public ResponseEntity<RestaurantResponseDTO> update(@PathVariable UUID id, @RequestBody RestaurantRequestDTO restaurantRequestDTO) {
        var restaurant = restaurantMapper.toDomain(restaurantRequestDTO);
        var updatedRestaurant = updateRestaurantUseCase.execute(id, restaurant);
        return ResponseEntity.ok(restaurantMapper.toDTO(updatedRestaurant));
    }
}