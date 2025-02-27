package app.jabafood.cleanarch.infrastructure.persistence.mappers;

import app.jabafood.cleanarch.domain.entities.User;
import app.jabafood.cleanarch.infrastructure.persistence.entities.RestaurantEntity;
import app.jabafood.cleanarch.infrastructure.persistence.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {

    @Named("mapRestaurantsToIds")
    static List<UUID> mapRestaurantsToIds(List<RestaurantEntity> entities) {
        return entities == null ? null : entities.stream()
                .map(RestaurantEntity::getId)
                .collect(Collectors.toList());
    }

    @Named("mapIdsToRestaurants")
    static List<RestaurantEntity> mapIdsToRestaurants(List<UUID> ids) {
        return ids == null ? null : ids.stream()
                .map(id -> {
                    RestaurantEntity restaurant = new RestaurantEntity();
                    restaurant.setId(id);
                    return restaurant;
                })
                .collect(Collectors.toList());
    }

    @Mapping(source = "restaurants", target = "restaurants", qualifiedByName = "mapRestaurantsToIds")
    User toDomain(UserEntity entity);

    @Mapping(source = "restaurants", target = "restaurants", qualifiedByName = "mapIdsToRestaurants")
    UserEntity toEntity(User user);
}
