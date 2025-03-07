package app.jabafood.cleanarch.interfaceAdapters.mappers;

import app.jabafood.cleanarch.domain.entities.MenuItem;
import app.jabafood.cleanarch.interfaceAdapters.dto.MenuItemRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MenuItemMapper {
    MenuItemRequestDTO toDTO(MenuItem menuItem);

    @Mapping(source = "restaurantId", target = "restaurant.id")
    MenuItem toDomain(MenuItemRequestDTO dto);

    List<MenuItemRequestDTO> toDTOList(List<MenuItem> menuItems);
}