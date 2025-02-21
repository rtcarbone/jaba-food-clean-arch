package app.jabafood.cleanarch.interfaceAdapters.mappers;

import app.jabafood.cleanarch.domain.entities.MenuItem;
import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.interfaceAdapters.dto.MenuItemDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MenuItemMapper {

    public MenuItemDTO toDTO(MenuItem menuItem) {
        return new MenuItemDTO(
                menuItem.getId(),
                menuItem.getName(),
                menuItem.getDescription(),
                menuItem.getPrice(),
                menuItem.isInRestaurantOnly(),
                menuItem.getImagePath(),
                menuItem.getRestaurant() != null ? menuItem.getRestaurant()
                        .getId() : null
        );
    }

    public MenuItem toEntity(MenuItemDTO dto) {
        return new MenuItem(
                dto.id(),
                dto.name(),
                dto.description(),
                dto.price(),
                dto.inRestaurantOnly(),
                dto.imagePath(),
                new Restaurant(dto.restaurantId(), null, null, null, null)
        );
    }

    public List<MenuItemDTO> toDTOList(List<MenuItem> menuItems) {
        return menuItems.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<MenuItem> toEntityList(List<MenuItemDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

}
