package app.jabafood.cleanarch.interfaceAdapters.controllers;

import app.jabafood.cleanarch.application.useCases.restaurant.*;
import app.jabafood.cleanarch.interfaceAdapters.dto.RestaurantDTO;
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
    public ResponseEntity<RestaurantDTO> create(@RequestBody RestaurantDTO restaurantDTO) {
        var restaurant = restaurantMapper.toDomain(restaurantDTO);
        var createdRestaurant = createRestaurantUseCase.execute(restaurant);
        return ResponseEntity.ok(restaurantMapper.toDTO(createdRestaurant));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDTO> findById(@PathVariable UUID id) {
        var restaurant = getRestaurantByIdUseCase.execute(id);
        return ResponseEntity.ok(restaurantMapper.toDTO(restaurant));
    }

    @GetMapping
    public ResponseEntity<List<RestaurantDTO>> listAll() {
        return ResponseEntity.ok(restaurantMapper.toDTOList(listRestaurantsUseCase.execute()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantDTO> update(@PathVariable UUID id, @RequestBody RestaurantDTO restaurantDTO) {
        var restaurant = restaurantMapper.toDomain(restaurantDTO);
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
