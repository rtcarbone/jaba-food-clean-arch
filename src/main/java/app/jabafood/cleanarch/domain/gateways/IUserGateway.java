package app.jabafood.cleanarch.domain.gateways;

import app.jabafood.cleanarch.domain.entities.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserGateway {
    User save(User user);

    Optional<User> findByEmail(String email);

    Optional<User> findById(UUID id);

    Optional<User> findByLogin(String login);

    List<User> findAll();

    void delete(UUID id);
}
