package app.jabafood.cleanarch.application.useCases.restaurant;

import app.jabafood.cleanarch.domain.repositories.RestaurantRepository;

import java.util.UUID;

public class DeleteRestaurantUseCase {
    private final RestaurantRepository restaurantRepository;

    public DeleteRestaurantUseCase(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public void execute(UUID id) {
        if (restaurantRepository.findById(id).isEmpty()) {
            throw new RuntimeException("Restaurant not found");
        }
        restaurantRepository.delete(id);
    }
}
