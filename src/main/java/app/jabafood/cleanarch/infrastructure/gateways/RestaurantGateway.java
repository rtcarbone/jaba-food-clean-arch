package app.jabafood.cleanarch.infrastructure.gateways;

import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.domain.gateways.IRestaurantGateway;
import app.jabafood.cleanarch.infrastructure.persistence.mappers.RestaurantEntityMapper;
import app.jabafood.cleanarch.infrastructure.persistence.repositories.RestaurantJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RestaurantGateway implements IRestaurantGateway {
    private final RestaurantJpaRepository jpaRepository;
    private final RestaurantEntityMapper mapper;

    @Override
    public Restaurant save(Restaurant restaurant) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(restaurant)));
    }

    @Override
    public Optional<Restaurant> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Restaurant> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Restaurant> findByOwnerId(UUID ownerId) {
        return jpaRepository.findByOwnerId(ownerId)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(UUID id) {
        jpaRepository.deleteById(id);
    }
}