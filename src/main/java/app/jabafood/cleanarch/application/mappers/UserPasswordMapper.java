package app.jabafood.cleanarch.application.mappers;

import app.jabafood.cleanarch.domain.valueObjects.UserPassword;
import app.jabafood.cleanarch.application.dto.UserUpdatePasswordRequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserPasswordMapper {
    UserPassword toValueObject(UserUpdatePasswordRequestDTO dto);
}
