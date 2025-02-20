package app.jabafood.cleanarch.domain.repositories;

import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.domain.entities.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RestaurantRepository {
    Restaurant save(Restaurant restaurant);

    Optional<Restaurant> findById(UUID id);

    List<Restaurant> findAll();

    void delete(UUID id);
}
