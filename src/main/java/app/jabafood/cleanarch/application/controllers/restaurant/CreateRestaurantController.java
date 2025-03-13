package app.jabafood.cleanarch.application.controllers.restaurant;

import app.jabafood.cleanarch.domain.useCases.restaurant.CreateRestaurantUseCase;
import app.jabafood.cleanarch.application.dto.RestaurantRequestDTO;
import app.jabafood.cleanarch.application.dto.RestaurantResponseDTO;
import app.jabafood.cleanarch.application.mappers.RestaurantMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/restaurants/create")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Create Restaurant", description = "Create Restaurant API")
public class CreateRestaurantController {
    private final CreateRestaurantUseCase createRestaurantUseCase;
    private final RestaurantMapper restaurantMapper;

    @PostMapping
    public ResponseEntity<RestaurantResponseDTO> create(@RequestBody RestaurantRequestDTO restaurantRequestDTO) {
        var restaurant = restaurantMapper.toDomain(restaurantRequestDTO);
        var createdRestaurant = createRestaurantUseCase.execute(restaurant);
        return ResponseEntity.ok(restaurantMapper.toDTO(createdRestaurant));
    }
}
