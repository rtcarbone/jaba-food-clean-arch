package app.jabafood.cleanarch.infrastructure.persistence.repositories.impl;

import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.domain.repositories.RestaurantRepository;
import app.jabafood.cleanarch.infrastructure.persistence.mappers.RestaurantEntityMapper;
import app.jabafood.cleanarch.infrastructure.persistence.repositories.RestaurantJpaRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepository {
    private final RestaurantJpaRepository jpaRepository;
    private final RestaurantEntityMapper mapper;

    public RestaurantRepositoryImpl(RestaurantJpaRepository jpaRepository, @Qualifier("restaurantEntityMapper") RestaurantEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

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
    public void delete(UUID id) {
        jpaRepository.deleteById(id);
    }
}