package app.jabafood.cleanarch.infrastructure.persistence.mappers;

import app.jabafood.cleanarch.domain.entities.MenuItem;
import app.jabafood.cleanarch.infrastructure.persistence.entities.MenuItemEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MenuItemEntityMapper {
    MenuItem toDomain(MenuItemEntity entity);

    MenuItemEntity toEntity(MenuItem menuItem);
}
