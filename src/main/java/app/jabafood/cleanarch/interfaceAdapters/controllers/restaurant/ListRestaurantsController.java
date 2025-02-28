package app.jabafood.cleanarch.interfaceAdapters.controllers.restaurant;

import app.jabafood.cleanarch.application.useCases.restaurant.ListRestaurantsUseCase;
import app.jabafood.cleanarch.interfaceAdapters.dto.RestaurantResponseDTO;
import app.jabafood.cleanarch.interfaceAdapters.mappers.RestaurantMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/restaurants/list")
public class ListRestaurantsController {

    private final ListRestaurantsUseCase listRestaurantsUseCase;
    private final RestaurantMapper restaurantMapper;

    public ListRestaurantsController(ListRestaurantsUseCase listRestaurantsUseCase, RestaurantMapper restaurantMapper) {
        this.listRestaurantsUseCase = listRestaurantsUseCase;
        this.restaurantMapper = restaurantMapper;
    }

    @GetMapping
    public ResponseEntity<List<RestaurantResponseDTO>> list() {
        return ResponseEntity.ok(restaurantMapper.toDTOList(listRestaurantsUseCase.execute()));
    }
}
