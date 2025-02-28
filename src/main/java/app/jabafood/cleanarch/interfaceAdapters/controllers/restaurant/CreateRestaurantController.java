package app.jabafood.cleanarch.interfaceAdapters.controllers.restaurant;

import app.jabafood.cleanarch.application.useCases.restaurant.CreateRestaurantUseCase;
import app.jabafood.cleanarch.interfaceAdapters.dto.RestaurantRequestDTO;
import app.jabafood.cleanarch.interfaceAdapters.dto.RestaurantResponseDTO;
import app.jabafood.cleanarch.interfaceAdapters.mappers.RestaurantMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/restaurants/create")
public class CreateRestaurantController {

    private final CreateRestaurantUseCase createRestaurantUseCase;
    private final RestaurantMapper restaurantMapper;

    public CreateRestaurantController(CreateRestaurantUseCase createRestaurantUseCase,
                                      RestaurantMapper restaurantMapper) {
        this.createRestaurantUseCase = createRestaurantUseCase;
        this.restaurantMapper = restaurantMapper;
    }

    @PostMapping
    public ResponseEntity<RestaurantResponseDTO> create(@RequestBody RestaurantRequestDTO restaurantRequestDTO) {
        var restaurant = restaurantMapper.toDomain(restaurantRequestDTO);
        var createdRestaurant = createRestaurantUseCase.execute(restaurant);
        return ResponseEntity.ok(restaurantMapper.toDTO(createdRestaurant));
    }

}
