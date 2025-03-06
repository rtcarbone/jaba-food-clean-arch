package app.jabafood.cleanarch.application.useCases.restaurant;

import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.domain.entities.User;
import app.jabafood.cleanarch.domain.enums.UserType;
import app.jabafood.cleanarch.domain.exceptions.RestaurantMandatoryFieldException;
import app.jabafood.cleanarch.domain.exceptions.RestaurantOwnerInvalidException;
import app.jabafood.cleanarch.domain.exceptions.UserNotFoundException;
import app.jabafood.cleanarch.domain.gateways.IRestaurantGateway;
import app.jabafood.cleanarch.domain.gateways.IUserGateway;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class CreateRestaurantUseCase {
    private final IRestaurantGateway restaurantGateway;
    private final IUserGateway userGateway;

    public Restaurant execute(Restaurant restaurant) {

        if (restaurant.getOwner() == null || restaurant.getOwner()
                                                     .getId() == null) {
            throw new RestaurantMandatoryFieldException("owner");
        }

        Optional<User> existingOwner = userGateway.findById(restaurant.getOwner()
                                                                    .getId());
        if (existingOwner.isEmpty()) {
            throw new UserNotFoundException(restaurant.getOwner()
                                                    .getId());
        }

        if (!UserType.RESTAURANT_OWNER.equals(existingOwner.get()
                                                      .getUserType())) {
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