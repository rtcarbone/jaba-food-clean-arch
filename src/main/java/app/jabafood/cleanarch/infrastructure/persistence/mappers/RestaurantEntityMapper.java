package app.jabafood.cleanarch.infrastructure.persistence.mappers;

import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.infrastructure.persistence.entities.RestaurantEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RestaurantEntityMapper {
    Restaurant toDomain(RestaurantEntity entity);

    RestaurantEntity toEntity(Restaurant restaurant);
}