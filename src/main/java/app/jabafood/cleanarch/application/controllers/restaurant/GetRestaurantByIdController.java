package app.jabafood.cleanarch.application.controllers.restaurant;

import app.jabafood.cleanarch.application.dto.RestaurantResponseDTO;
import app.jabafood.cleanarch.application.mappers.RestaurantMapper;
import app.jabafood.cleanarch.domain.useCases.restaurant.GetRestaurantByIdUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Restaurant", description = "Restaurant Management API")
public class GetRestaurantByIdController {
    private final GetRestaurantByIdUseCase getRestaurantByIdUseCase;
    private final RestaurantMapper restaurantMapper;

    @Operation(summary = "Get restaurant by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurant found successfully"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    @GetMapping
    public ResponseEntity<RestaurantResponseDTO> findById(@PathVariable UUID id) {
        var restaurant = getRestaurantByIdUseCase.execute(id);
        return ResponseEntity.ok(restaurantMapper.toDTO(restaurant));
    }
}