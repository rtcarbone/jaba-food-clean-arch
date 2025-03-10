package app.jabafood.cleanarch.application.controllers.restaurant;

import app.jabafood.cleanarch.domain.useCases.restaurant.ListRestaurantsByOwnerUseCase;
import app.jabafood.cleanarch.application.dto.RestaurantResponseDTO;
import app.jabafood.cleanarch.application.mappers.RestaurantMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/restaurants/list/owner/{ownerId}")
@RequiredArgsConstructor
public class ListRestaurantsByOwnerController {
    private final ListRestaurantsByOwnerUseCase listRestaurantsByOwnerUseCase;
    private final RestaurantMapper restaurantMapper;

    @GetMapping
    public ResponseEntity<List<RestaurantResponseDTO>> findByOwner(@PathVariable UUID ownerId) {
        return ResponseEntity.ok(restaurantMapper.toDTOList(listRestaurantsByOwnerUseCase.execute(ownerId)));
    }
}