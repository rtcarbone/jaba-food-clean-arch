package app.jabafood.cleanarch.application.useCases.restaurant;

import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.domain.repositories.RestaurantRepository;

public class CreateRestaurantUseCase {
    private final RestaurantRepository restaurantRepository;

    public CreateRestaurantUseCase(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Restaurant execute(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }
}