package app.jabafood.cleanarch.application.useCases.restaurant;

import app.jabafood.cleanarch.domain.gateways.IRestaurantGateway;

import java.util.UUID;

public class DeleteRestaurantUseCase {
    private final IRestaurantGateway restaurantGateway;

    public DeleteRestaurantUseCase(IRestaurantGateway restaurantGateway) {
        this.restaurantGateway = restaurantGateway;
    }

    public void execute(UUID id) {
        if (restaurantGateway.findById(id).isEmpty()) {
            throw new RuntimeException("Restaurant not found");
        }
        restaurantGateway.delete(id);
    }
}
