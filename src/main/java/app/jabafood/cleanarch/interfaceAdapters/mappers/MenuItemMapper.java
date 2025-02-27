package app.jabafood.cleanarch.interfaceAdapters.mappers;

import app.jabafood.cleanarch.domain.entities.MenuItem;
import app.jabafood.cleanarch.interfaceAdapters.dto.MenuItemDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MenuItemMapper {
    MenuItemDTO toDTO(MenuItem menuItem);

    MenuItem toDomain(MenuItemDTO dto);

    List<MenuItemDTO> toDTOList(List<MenuItem> menuItems);
}