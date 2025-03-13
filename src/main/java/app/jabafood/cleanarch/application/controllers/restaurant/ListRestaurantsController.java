package app.jabafood.cleanarch.application.controllers.restaurant;

import app.jabafood.cleanarch.application.dto.RestaurantResponseDTO;
import app.jabafood.cleanarch.application.mappers.RestaurantMapper;
import app.jabafood.cleanarch.domain.useCases.restaurant.ListRestaurantsUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/restaurants/list")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "List Restaurants", description = "List Restaurants API")
public class ListRestaurantsController {
    private final ListRestaurantsUseCase listRestaurantsUseCase;
    private final RestaurantMapper restaurantMapper;

    @GetMapping
    public ResponseEntity<List<RestaurantResponseDTO>> list() {
        return ResponseEntity.ok(restaurantMapper.toDTOList(listRestaurantsUseCase.execute()));
    }
}