package app.jabafood.cleanarch.application.mappers;

import app.jabafood.cleanarch.domain.entities.MenuItem;
import app.jabafood.cleanarch.application.dto.MenuItemRequestDTO;
import app.jabafood.cleanarch.application.dto.MenuItemResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MenuItemMapper {
    MenuItemResponseDTO toDTO(MenuItem menuItem);

    @Mapping(source = "restaurantId", target = "restaurant.id")
    MenuItem toDomain(MenuItemRequestDTO dto);

    List<MenuItemResponseDTO> toDTOList(List<MenuItem> menuItems);
}