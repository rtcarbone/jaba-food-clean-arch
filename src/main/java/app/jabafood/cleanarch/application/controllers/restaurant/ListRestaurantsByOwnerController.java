package app.jabafood.cleanarch.application.controllers.restaurant;

import app.jabafood.cleanarch.domain.useCases.restaurant.ListRestaurantsByOwnerUseCase;
import app.jabafood.cleanarch.application.dto.RestaurantResponseDTO;
import app.jabafood.cleanarch.application.mappers.RestaurantMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Tag(name = "Restaurant", description = "Restaurant Management API")
public class ListRestaurantsByOwnerController {
    private final ListRestaurantsByOwnerUseCase listRestaurantsByOwnerUseCase;
    private final RestaurantMapper restaurantMapper;

    @Operation(summary = "List all restaurants owned by a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurants owned by user retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Owner not found")
    })
    @GetMapping
    public ResponseEntity<List<RestaurantResponseDTO>> findByOwner(@PathVariable UUID ownerId) {
        return ResponseEntity.ok(restaurantMapper.toDTOList(listRestaurantsByOwnerUseCase.execute(ownerId)));
    }
}