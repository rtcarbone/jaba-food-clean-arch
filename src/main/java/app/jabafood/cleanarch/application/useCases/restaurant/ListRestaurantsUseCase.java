package app.jabafood.cleanarch.application.useCases.restaurant;

import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.domain.repositories.RestaurantRepository;

import java.util.List;

public class ListRestaurantsUseCase {
    private final RestaurantRepository restaurantRepository;

    public ListRestaurantsUseCase(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<Restaurant> execute() {
        return restaurantRepository.findAll();
    }
}
