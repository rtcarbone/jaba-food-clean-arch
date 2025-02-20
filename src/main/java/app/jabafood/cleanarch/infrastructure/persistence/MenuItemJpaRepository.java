package app.jabafood.cleanarch.infrastructure.persistence;

import app.jabafood.cleanarch.domain.entities.MenuItem;
import app.jabafood.cleanarch.domain.repositories.MenuItemRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MenuItemJpaRepository extends JpaRepository<MenuItem, UUID>, MenuItemRepository {
    List<MenuItem> findByRestaurantId(UUID restaurantId);
}