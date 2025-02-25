package app.jabafood.cleanarch.interfaceAdapters.mappers;

import app.jabafood.cleanarch.domain.entities.MenuItem;
import app.jabafood.cleanarch.interfaceAdapters.dto.MenuItemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MenuItemMapper {
    MenuItemMapper INSTANCE = Mappers.getMapper(MenuItemMapper.class);

    MenuItemDTO toDTO(MenuItem menuItem);

    MenuItem toDomain(MenuItemDTO dto);

    List<MenuItemDTO> toDTOList(List<MenuItem> menuItems);

    List<MenuItem> toDomainList(List<MenuItemDTO> dtos);
}