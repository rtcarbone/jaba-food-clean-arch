package app.jabafood.cleanarch.application.controllers.restaurant;

import app.jabafood.cleanarch.domain.useCases.restaurant.GetRestaurantByIdUseCase;
import app.jabafood.cleanarch.application.dto.RestaurantResponseDTO;
import app.jabafood.cleanarch.application.mappers.RestaurantMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/restaurants/{id}")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Get Restaurant By ID", description = "Get Restaurant By ID API")
public class GetRestaurantByIdController {
    private final GetRestaurantByIdUseCase getRestaurantByIdUseCase;
    private final RestaurantMapper restaurantMapper;

    @GetMapping
    public ResponseEntity<RestaurantResponseDTO> findById(@PathVariable UUID id) {
        var restaurant = getRestaurantByIdUseCase.execute(id);
        return ResponseEntity.ok(restaurantMapper.toDTO(restaurant));
    }
}