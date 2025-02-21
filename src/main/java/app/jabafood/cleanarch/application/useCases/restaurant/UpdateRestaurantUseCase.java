package app.jabafood.cleanarch.application.useCases.restaurant;

import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.domain.repositories.RestaurantRepository;

import java.util.UUID;

public class UpdateRestaurantUseCase {
    private final RestaurantRepository restaurantRepository;

    public UpdateRestaurantUseCase(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Restaurant execute(UUID id, Restaurant updatedData) {
        Restaurant existingRestaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        Restaurant updatedRestaurant = new Restaurant(
                existingRestaurant.getId(),
                updatedData.getName(),
                updatedData.getCuisineType(),
                updatedData.getOpeningHours(),
                existingRestaurant.getOwner()
        );

        return restaurantRepository.save(updatedRestaurant);
    }
}