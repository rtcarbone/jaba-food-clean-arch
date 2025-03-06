package app.jabafood.cleanarch.interfaceAdapters.mappers;

import app.jabafood.cleanarch.domain.entities.User;
import app.jabafood.cleanarch.domain.entities.UserPassword;
import app.jabafood.cleanarch.interfaceAdapters.dto.UserRequestDTO;
import app.jabafood.cleanarch.interfaceAdapters.dto.UserResponseDTO;
import app.jabafood.cleanarch.interfaceAdapters.dto.UserUpdatePasswordRequestDTO;
import app.jabafood.cleanarch.interfaceAdapters.dto.UserUpdateRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDTO toDTO(User user);

    @Mapping(target = "userType", constant = "CUSTOMER")
    User toDomain(UserRequestDTO dto);
    User toDomainUpdate(UserUpdateRequestDTO dto);
    UserPassword toDomainPasswordUpdate(UserUpdatePasswordRequestDTO dto);

    List<UserResponseDTO> toDTOList(List<User> users);
}
