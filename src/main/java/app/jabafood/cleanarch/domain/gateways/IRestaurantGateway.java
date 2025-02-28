package app.jabafood.cleanarch.domain.gateways;

import app.jabafood.cleanarch.domain.entities.Restaurant;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IRestaurantGateway {
    Restaurant save(Restaurant restaurant);

    Optional<Restaurant> findById(UUID id);

    List<Restaurant> findAll();

    List<Restaurant> findByOwnerId(UUID ownerId);

    void delete(UUID id);
}
