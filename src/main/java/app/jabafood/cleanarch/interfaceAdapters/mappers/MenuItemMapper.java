package app.jabafood.cleanarch.interfaceAdapters.mappers;

import app.jabafood.cleanarch.domain.entities.MenuItem;
import app.jabafood.cleanarch.interfaceAdapters.dto.MenuItemRequestDTO;
import app.jabafood.cleanarch.interfaceAdapters.dto.MenuItemResponseDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MenuItemMapper {
    MenuItemResponseDTO toDTO(MenuItem menuItem);

    MenuItem toDomain(MenuItemRequestDTO dto);

    List<MenuItemResponseDTO> toDTOList(List<MenuItem> menuItems);
}