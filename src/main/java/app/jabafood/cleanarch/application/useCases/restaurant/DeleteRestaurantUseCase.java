package app.jabafood.cleanarch.application.useCases.restaurant;

import app.jabafood.cleanarch.domain.exceptions.RestaurantNotFoundException;
import app.jabafood.cleanarch.domain.gateways.IRestaurantGateway;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class DeleteRestaurantUseCase {
    private final IRestaurantGateway restaurantGateway;

    public void execute(UUID id) {
        if (restaurantGateway.findById(id)
                .isEmpty()) {
            throw new RestaurantNotFoundException(id);
        }
        restaurantGateway.delete(id);
    }
}
