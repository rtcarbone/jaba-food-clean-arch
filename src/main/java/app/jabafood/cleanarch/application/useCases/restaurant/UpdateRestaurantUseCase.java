package app.jabafood.cleanarch.application.useCases.restaurant;

import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.domain.exceptions.RestaurantNotFoundException;
import app.jabafood.cleanarch.domain.gateways.IRestaurantGateway;

import java.util.UUID;

public class UpdateRestaurantUseCase {
    private final IRestaurantGateway restaurantGateway;

    public UpdateRestaurantUseCase(IRestaurantGateway restaurantGateway) {
        this.restaurantGateway = restaurantGateway;
    }

    public Restaurant execute(UUID id, Restaurant updatedData) {
        Restaurant existingRestaurant = restaurantGateway.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException(id));

        Restaurant updatedRestaurant = existingRestaurant.copyWith(
                updatedData.getName(),
                updatedData.getAddress(),
                updatedData.getCuisineType(),
                updatedData.getOpeningTime(),
                updatedData.getClosingTime()
        );

        return restaurantGateway.save(updatedRestaurant);
    }
}