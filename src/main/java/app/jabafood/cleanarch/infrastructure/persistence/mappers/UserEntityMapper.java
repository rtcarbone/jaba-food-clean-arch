package app.jabafood.cleanarch.infrastructure.persistence.mappers;

import app.jabafood.cleanarch.domain.entities.User;
import app.jabafood.cleanarch.infrastructure.persistence.entities.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {
    User toDomain(UserEntity entity);

    UserEntity toEntity(User user);
}
