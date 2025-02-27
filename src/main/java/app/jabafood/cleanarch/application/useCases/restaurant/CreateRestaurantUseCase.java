package app.jabafood.cleanarch.application.useCases.restaurant;

import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.domain.exceptions.RestaurantMandatoryFieldException;
import app.jabafood.cleanarch.domain.exceptions.RestaurantOwnerInvalidException;
import app.jabafood.cleanarch.domain.gateways.IRestaurantGateway;
import app.jabafood.cleanarch.domain.gateways.IUserGateway;

public class CreateRestaurantUseCase {
    private final IRestaurantGateway restaurantGateway;
    private final IUserGateway userGateway;

    public CreateRestaurantUseCase(IRestaurantGateway restaurantGateway, IUserGateway userGateway) {
        this.restaurantGateway = restaurantGateway;
        this.userGateway = userGateway;
    }

    public Restaurant execute(Restaurant restaurant) {

        if (restaurant.getOwner() == null) {
            throw new RestaurantMandatoryFieldException("owner");
        }

        if (userGateway.findById(restaurant.getOwner()
                                         .getId())
                .isEmpty()) {
            throw new RestaurantOwnerInvalidException(restaurant.getOwner()
                                                              .getId());
        }

        restaurant.validate();

        return restaurantGateway.save(restaurant);
    }
}