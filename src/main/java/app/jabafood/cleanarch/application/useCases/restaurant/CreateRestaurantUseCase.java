package app.jabafood.cleanarch.application.useCases.restaurant;

import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.domain.entities.User;
import app.jabafood.cleanarch.domain.exceptions.RestaurantMandatoryFieldException;
import app.jabafood.cleanarch.domain.exceptions.RestaurantOwnerInvalidException;
import app.jabafood.cleanarch.domain.gateways.IRestaurantGateway;
import app.jabafood.cleanarch.domain.gateways.IUserGateway;

import java.util.Optional;

public class CreateRestaurantUseCase {
    private final IRestaurantGateway restaurantGateway;
    private final IUserGateway userGateway;

    public CreateRestaurantUseCase(IRestaurantGateway restaurantGateway, IUserGateway userGateway) {
        this.restaurantGateway = restaurantGateway;
        this.userGateway = userGateway;
    }

    public Restaurant execute(Restaurant restaurant) {

        if (restaurant.getOwner() == null || restaurant.getOwner()
                                                     .getId() == null) {
            throw new RestaurantMandatoryFieldException("owner");
        }

        Optional<User> existingOwner = userGateway.findById(restaurant.getOwner()
                                                                    .getId());
        if (existingOwner.isEmpty()) {
            throw new RestaurantOwnerInvalidException(restaurant.getOwner()
                                                              .getId());
        }

        Restaurant newRestaurant = restaurant.copyWith(
                restaurant.getName(),
                restaurant.getAddress(),
                restaurant.getCuisineType(),
                restaurant.getOpeningTime(),
                restaurant.getClosingTime(),
                existingOwner.get()
        );

        newRestaurant.validate();

        return restaurantGateway.save(newRestaurant);
    }
}