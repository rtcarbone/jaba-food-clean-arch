package app.jabafood.cleanarch.application.useCases.restaurant;

import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.domain.gateways.IRestaurantGateway;

public class CreateRestaurantUseCase {
    private final IRestaurantGateway restaurantGateway;

    public CreateRestaurantUseCase(IRestaurantGateway restaurantGateway) {
        this.restaurantGateway = restaurantGateway;
    }

    public Restaurant execute(Restaurant restaurant) {
        return restaurantGateway.save(restaurant);
    }
}