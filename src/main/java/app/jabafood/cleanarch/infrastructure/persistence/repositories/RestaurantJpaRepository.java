package app.jabafood.cleanarch.infrastructure.persistence.repositories;

import app.jabafood.cleanarch.infrastructure.persistence.entities.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RestaurantJpaRepository extends JpaRepository<RestaurantEntity, UUID> {
    List<RestaurantEntity> findByOwnerId(UUID ownerId);
}