package app.jabafood.cleanarch.infrastructure.persistence.gateways;

import app.jabafood.cleanarch.domain.entities.User;
import app.jabafood.cleanarch.domain.gateways.IUserGateway;
import app.jabafood.cleanarch.infrastructure.persistence.mappers.UserEntityMapper;
import app.jabafood.cleanarch.infrastructure.persistence.repositories.UserJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class UserGateway implements IUserGateway {
    private final UserJpaRepository jpaRepository;
    private final UserEntityMapper mapper;

    public UserGateway(UserJpaRepository jpaRepository, UserEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public User save(User user) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(user)));
    }

    @Override
    public Optional<User> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return jpaRepository.findByUsername(username)
                .map(mapper::toDomain);
    }

    @Override
    public List<User> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(UUID id) {
        jpaRepository.deleteById(id);
    }
}