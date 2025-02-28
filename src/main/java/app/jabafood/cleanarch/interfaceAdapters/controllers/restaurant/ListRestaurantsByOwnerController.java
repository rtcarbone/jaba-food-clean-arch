package app.jabafood.cleanarch.interfaceAdapters.controllers.restaurant;

import app.jabafood.cleanarch.application.useCases.restaurant.ListRestaurantsByOwnerUseCase;
import app.jabafood.cleanarch.interfaceAdapters.dto.RestaurantResponseDTO;
import app.jabafood.cleanarch.interfaceAdapters.mappers.RestaurantMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/restaurants/list/owner")
public class ListRestaurantsByOwnerController {

    private final ListRestaurantsByOwnerUseCase listRestaurantsByOwnerUseCase;
    private final RestaurantMapper restaurantMapper;

    public ListRestaurantsByOwnerController(ListRestaurantsByOwnerUseCase listRestaurantsByOwnerUseCase, RestaurantMapper restaurantMapper) {
        this.listRestaurantsByOwnerUseCase = listRestaurantsByOwnerUseCase;
        this.restaurantMapper = restaurantMapper;
    }

    @GetMapping("/{ownerId}")
    public ResponseEntity<List<RestaurantResponseDTO>> findByOwner(@PathVariable UUID ownerId) {
        return ResponseEntity.ok(restaurantMapper.toDTOList(listRestaurantsByOwnerUseCase.execute(ownerId)));
    }
}
