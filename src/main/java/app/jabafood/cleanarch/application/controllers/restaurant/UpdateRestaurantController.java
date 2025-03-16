package app.jabafood.cleanarch.application.controllers.restaurant;

import app.jabafood.cleanarch.application.dto.RestaurantRequestDTO;
import app.jabafood.cleanarch.application.dto.RestaurantResponseDTO;
import app.jabafood.cleanarch.application.mappers.RestaurantMapper;
import app.jabafood.cleanarch.domain.useCases.restaurant.UpdateRestaurantUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/restaurants/{id}/update")
@RequiredArgsConstructor
@Tag(name = "Restaurant", description = "Restaurant Management API")
public class UpdateRestaurantController {
    private final UpdateRestaurantUseCase updateRestaurantUseCase;
    private final RestaurantMapper restaurantMapper;

    @Operation(summary = "Update restaurant details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurant updated successfully"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PutMapping
    public ResponseEntity<RestaurantResponseDTO> update(@PathVariable UUID id, @RequestBody RestaurantRequestDTO restaurantRequestDTO) {
        var restaurant = restaurantMapper.toDomain(restaurantRequestDTO);
        var updatedRestaurant = updateRestaurantUseCase.execute(id, restaurant);
        return ResponseEntity.ok(restaurantMapper.toDTO(updatedRestaurant));
    }
}