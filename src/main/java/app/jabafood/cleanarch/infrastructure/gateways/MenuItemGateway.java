package app.jabafood.cleanarch.infrastructure.gateways;

import app.jabafood.cleanarch.domain.entities.MenuItem;
import app.jabafood.cleanarch.domain.gateways.IMenuItemGateway;
import app.jabafood.cleanarch.infrastructure.persistence.mappers.MenuItemEntityMapper;
import app.jabafood.cleanarch.infrastructure.persistence.repositories.MenuItemJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MenuItemGateway implements IMenuItemGateway {
    private final MenuItemJpaRepository jpaRepository;
    private final MenuItemEntityMapper mapper;

    @Override
    public MenuItem save(MenuItem menuItem) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(menuItem)));
    }

    @Override
    public Optional<MenuItem> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<MenuItem> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<MenuItem> findByRestaurantId(UUID restaurantId) {
        return jpaRepository.findByRestaurantId(restaurantId)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(UUID id) {
        jpaRepository.deleteById(id);
    }
}
