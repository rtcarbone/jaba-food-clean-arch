package app.jabafood.cleanarch.infrastructure.persistence.mappers;

import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.infrastructure.persistence.entities.MenuItemEntity;
import app.jabafood.cleanarch.infrastructure.persistence.entities.RestaurantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RestaurantEntityMapper {

    @Named("mapMenuItemsToIds")
    static List<UUID> mapMenuItemsToIds(List<MenuItemEntity> menuItems) {
        return menuItems == null ? null : menuItems.stream()
                .map(MenuItemEntity::getId)
                .collect(Collectors.toList());
    }

    @Named("mapIdsToMenuItems")
    static List<MenuItemEntity> mapIdsToMenuItems(List<UUID> ids) {
        return ids == null ? null : ids.stream()
                .map(id -> {
                    MenuItemEntity menuItem = new MenuItemEntity();
                    menuItem.setId(id);
                    return menuItem;
                })
                .collect(Collectors.toList());
    }

    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "menuItems", target = "menuItems", qualifiedByName = "mapMenuItemsToIds")
    Restaurant toDomain(RestaurantEntity entity);

    @Mapping(source = "ownerId", target = "owner.id")
    @Mapping(source = "menuItems", target = "menuItems", qualifiedByName = "mapIdsToMenuItems")
    RestaurantEntity toEntity(Restaurant restaurant);
}