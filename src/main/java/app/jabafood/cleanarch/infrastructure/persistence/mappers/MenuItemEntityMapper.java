package app.jabafood.cleanarch.infrastructure.persistence.mappers;

import app.jabafood.cleanarch.domain.entities.MenuItem;
import app.jabafood.cleanarch.infrastructure.persistence.entities.MenuItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MenuItemEntityMapper {
    @Mapping(source = "restaurant.id", target = "restaurantId")
    default MenuItem toDomain(MenuItemEntity entity) {
        return new MenuItem(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.isInRestaurantOnly(),
                entity.getImagePath(),
                entity.getRestaurant()
                        .getId()
        );
    }

    MenuItemEntity toEntity(MenuItem menuItem);
}
