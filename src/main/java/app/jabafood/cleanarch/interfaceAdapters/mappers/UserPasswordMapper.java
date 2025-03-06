package app.jabafood.cleanarch.interfaceAdapters.mappers;

import app.jabafood.cleanarch.domain.valueObjects.UserPassword;
import app.jabafood.cleanarch.interfaceAdapters.dto.UserUpdatePasswordRequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserPasswordMapper {
    UserPassword toValueObject(UserUpdatePasswordRequestDTO dto);
}
