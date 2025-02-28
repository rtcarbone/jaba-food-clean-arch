package app.jabafood.cleanarch.application.useCases.restaurant;

import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.domain.exceptions.UserNotFoundException;
import app.jabafood.cleanarch.domain.gateways.IRestaurantGateway;
import app.jabafood.cleanarch.domain.gateways.IUserGateway;

import java.util.List;
import java.util.UUID;

public class ListRestaurantsByOwnerUseCase {
    private final IRestaurantGateway restaurantGateway;
    private final IUserGateway userGateway;

    public ListRestaurantsByOwnerUseCase(IRestaurantGateway restaurantGateway, IUserGateway userGateway) {
        this.restaurantGateway = restaurantGateway;
        this.userGateway = userGateway;
    }

    public List<Restaurant> execute(UUID ownerId) {
        if (userGateway.findById(ownerId)
                .isEmpty()) {
            throw new UserNotFoundException(ownerId);
        }
        return restaurantGateway.findByOwnerId(ownerId);
    }
}
