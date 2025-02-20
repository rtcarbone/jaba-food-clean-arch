package app.jabafood.cleanarch.infrastructure.persistence;

import app.jabafood.cleanarch.domain.entities.User;
import app.jabafood.cleanarch.domain.repositories.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserJpaRepository extends JpaRepository<User, UUID>, UserRepository {
    Optional<User> findByUsername(String username);
}
