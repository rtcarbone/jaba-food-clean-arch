package app.jabafood.cleanarch.interfaceAdapters.controllers;

import app.jabafood.cleanarch.application.useCases.restaurant.*;
import app.jabafood.cleanarch.interfaceAdapters.dto.RestaurantRequestDTO;
import app.jabafood.cleanarch.interfaceAdapters.dto.RestaurantResponseDTO;
import app.jabafood.cleanarch.interfaceAdapters.mappers.RestaurantMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/restaurants")
public class RestaurantController {
    private final CreateRestaurantUseCase createRestaurantUseCase;
    private final GetRestaurantByIdUseCase getRestaurantByIdUseCase;
    private final UpdateRestaurantUseCase updateRestaurantUseCase;
    private final DeleteRestaurantUseCase deleteRestaurantUseCase;
    private final ListRestaurantsUseCase listRestaurantsUseCase;
    private final RestaurantMapper restaurantMapper;

    public RestaurantController(CreateRestaurantUseCase createRestaurantUseCase,
                                GetRestaurantByIdUseCase getRestaurantByIdUseCase,
                                UpdateRestaurantUseCase updateRestaurantUseCase,
                                DeleteRestaurantUseCase deleteRestaurantUseCase,
                                ListRestaurantsUseCase listRestaurantsUseCase,
                                RestaurantMapper restaurantMapper) {
        this.createRestaurantUseCase = createRestaurantUseCase;
        this.getRestaurantByIdUseCase = getRestaurantByIdUseCase;
        this.updateRestaurantUseCase = updateRestaurantUseCase;
        this.deleteRestaurantUseCase = deleteRestaurantUseCase;
        this.listRestaurantsUseCase = listRestaurantsUseCase;
        this.restaurantMapper = restaurantMapper;
    }

    @PostMapping
    public ResponseEntity<RestaurantResponseDTO> create(@RequestBody RestaurantRequestDTO restaurantRequestDTO) {
        var restaurant = restaurantMapper.toDomain(restaurantRequestDTO);
        var createdRestaurant = createRestaurantUseCase.execute(restaurant);
        return ResponseEntity.ok(restaurantMapper.toDTO(createdRestaurant));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponseDTO> findById(@PathVariable UUID id) {
        var restaurant = getRestaurantByIdUseCase.execute(id);
        return ResponseEntity.ok(restaurantMapper.toDTO(restaurant));
    }

    @GetMapping
    public ResponseEntity<List<RestaurantResponseDTO>> listAll() {
        return ResponseEntity.ok(restaurantMapper.toDTOList(listRestaurantsUseCase.execute()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantResponseDTO> update(@PathVariable UUID id, @RequestBody RestaurantRequestDTO restaurantRequestDTO) {
        var restaurant = restaurantMapper.toDomain(restaurantRequestDTO);
        var updatedRestaurant = updateRestaurantUseCase.execute(id, restaurant);
        return ResponseEntity.ok(restaurantMapper.toDTO(updatedRestaurant));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteRestaurantUseCase.execute(id);
        return ResponseEntity.noContent()
                .build();
    }

}
