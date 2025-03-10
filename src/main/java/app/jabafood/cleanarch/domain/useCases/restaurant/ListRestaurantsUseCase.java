package app.jabafood.cleanarch.domain.useCases.restaurant;

import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.domain.gateways.IRestaurantGateway;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ListRestaurantsUseCase {
    private final IRestaurantGateway restaurantGateway;

    public List<Restaurant> execute() {
        return restaurantGateway.findAll();
    }
}
