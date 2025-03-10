package app.jabafood.cleanarch.domain.useCases.restaurant;

import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.domain.exceptions.RestaurantNotFoundException;
import app.jabafood.cleanarch.domain.gateways.IRestaurantGateway;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class GetRestaurantByIdUseCase {
    private final IRestaurantGateway restaurantGateway;

    public Restaurant execute(UUID id) {
        return restaurantGateway.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException(id));
    }
}
