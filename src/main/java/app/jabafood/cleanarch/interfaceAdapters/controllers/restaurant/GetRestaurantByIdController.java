package app.jabafood.cleanarch.interfaceAdapters.controllers.restaurant;

import app.jabafood.cleanarch.application.useCases.restaurant.GetRestaurantByIdUseCase;
import app.jabafood.cleanarch.interfaceAdapters.dto.RestaurantResponseDTO;
import app.jabafood.cleanarch.interfaceAdapters.mappers.RestaurantMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/restaurants/{id}")
@RequiredArgsConstructor
public class GetRestaurantByIdController {
    private final GetRestaurantByIdUseCase getRestaurantByIdUseCase;
    private final RestaurantMapper restaurantMapper;

    @GetMapping
    public ResponseEntity<RestaurantResponseDTO> findById(@PathVariable UUID id) {
        var restaurant = getRestaurantByIdUseCase.execute(id);
        return ResponseEntity.ok(restaurantMapper.toDTO(restaurant));
    }
}