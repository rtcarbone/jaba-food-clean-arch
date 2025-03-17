package app.jabafood.cleanarch.domain.useCases.restaurant;

import app.jabafood.cleanarch.domain.exceptions.RestaurantNotFoundException;
import app.jabafood.cleanarch.domain.gateways.IRestaurantGateway;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class DeleteRestaurantUseCase {
    private final IRestaurantGateway restaurantGateway;

    public void execute(UUID id) {
        restaurantGateway.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException(id));

        restaurantGateway.delete(id);
    }
}
