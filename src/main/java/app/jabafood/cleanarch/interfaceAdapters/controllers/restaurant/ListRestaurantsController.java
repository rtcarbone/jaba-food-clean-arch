package app.jabafood.cleanarch.interfaceAdapters.controllers.restaurant;

import app.jabafood.cleanarch.application.useCases.restaurant.ListRestaurantsUseCase;
import app.jabafood.cleanarch.interfaceAdapters.dto.RestaurantResponseDTO;
import app.jabafood.cleanarch.interfaceAdapters.mappers.RestaurantMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/restaurants/list")
@RequiredArgsConstructor
public class ListRestaurantsController {
    private final ListRestaurantsUseCase listRestaurantsUseCase;
    private final RestaurantMapper restaurantMapper;

    @GetMapping
    public ResponseEntity<List<RestaurantResponseDTO>> list() {
        return ResponseEntity.ok(restaurantMapper.toDTOList(listRestaurantsUseCase.execute()));
    }
}
