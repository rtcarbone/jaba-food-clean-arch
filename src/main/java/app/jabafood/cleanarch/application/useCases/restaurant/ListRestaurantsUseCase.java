package app.jabafood.cleanarch.application.useCases.restaurant;

import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.domain.gateways.IRestaurantGateway;

import java.util.List;

public class ListRestaurantsUseCase {
    private final IRestaurantGateway restaurantGateway;

    public ListRestaurantsUseCase(IRestaurantGateway restaurantGateway) {
        this.restaurantGateway = restaurantGateway;
    }

    public List<Restaurant> execute() {
        return restaurantGateway.findAll();
    }
}
