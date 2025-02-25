package app.jabafood.cleanarch.application.useCases.restaurant;

import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.domain.gateways.IRestaurantGateway;

import java.util.UUID;

public class GetRestaurantByIdUseCase {
    private final IRestaurantGateway restaurantGateway;

    public GetRestaurantByIdUseCase(IRestaurantGateway restaurantGateway) {
        this.restaurantGateway = restaurantGateway;
    }

    public Restaurant execute(UUID id) {
        return restaurantGateway.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));
    }
}
