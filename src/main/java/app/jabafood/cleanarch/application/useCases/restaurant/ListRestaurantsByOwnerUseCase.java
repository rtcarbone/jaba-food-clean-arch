package app.jabafood.cleanarch.application.useCases.restaurant;

import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.domain.gateways.IRestaurantGateway;

import java.util.List;
import java.util.UUID;

public class ListRestaurantsByOwnerUseCase {
    private final IRestaurantGateway restaurantGateway;

    public ListRestaurantsByOwnerUseCase(IRestaurantGateway restaurantGateway) {
        this.restaurantGateway = restaurantGateway;
    }

    public List<Restaurant> execute(UUID ownerId) {
        return restaurantGateway.findByOwnerId(ownerId);
    }
}
