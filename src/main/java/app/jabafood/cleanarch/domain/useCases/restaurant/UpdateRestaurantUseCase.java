package app.jabafood.cleanarch.domain.useCases.restaurant;

import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.domain.exceptions.RestaurantNotFoundException;
import app.jabafood.cleanarch.domain.gateways.IRestaurantGateway;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class UpdateRestaurantUseCase {
    private final IRestaurantGateway restaurantGateway;

    public Restaurant execute(UUID id, Restaurant updatedData) {
        Restaurant existingRestaurant = restaurantGateway.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException(id));

        Restaurant updatedRestaurant = existingRestaurant.copyWith(
                updatedData.getName(),
                updatedData.getAddress(),
                updatedData.getCuisineType(),
                updatedData.getOpeningTime(),
                updatedData.getClosingTime(),
                existingRestaurant.getOwner()
        );

        updatedRestaurant.validate();

        return restaurantGateway.save(updatedRestaurant);
    }
}