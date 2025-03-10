package app.jabafood.cleanarch.domain.useCases.restaurant;

import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.domain.exceptions.UserNotFoundException;
import app.jabafood.cleanarch.domain.gateways.IRestaurantGateway;
import app.jabafood.cleanarch.domain.gateways.IUserGateway;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class ListRestaurantsByOwnerUseCase {
    private final IRestaurantGateway restaurantGateway;
    private final IUserGateway userGateway;

    public List<Restaurant> execute(UUID ownerId) {
        if (userGateway.findById(ownerId)
                .isEmpty()) {
            throw new UserNotFoundException(ownerId);
        }
        return restaurantGateway.findByOwnerId(ownerId);
    }
}