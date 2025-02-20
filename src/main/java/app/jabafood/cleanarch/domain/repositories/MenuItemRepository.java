package app.jabafood.cleanarch.domain.repositories;

import app.jabafood.cleanarch.domain.entities.MenuItem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MenuItemRepository {
    MenuItem save(MenuItem menuItem);

    Optional<MenuItem> findById(UUID id);

    List<MenuItem> findByRestaurantId(UUID restaurantId);

    void delete(UUID id);
}
