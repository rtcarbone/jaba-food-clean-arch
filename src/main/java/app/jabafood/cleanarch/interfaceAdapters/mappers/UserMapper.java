package app.jabafood.cleanarch.interfaceAdapters.mappers;

import app.jabafood.cleanarch.domain.entities.User;
import app.jabafood.cleanarch.interfaceAdapters.dto.UserRequestDTO;
import app.jabafood.cleanarch.interfaceAdapters.dto.UserResponseDTO;
import app.jabafood.cleanarch.interfaceAdapters.dto.UserUpdateRequestDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDTO toDTO(User user);

    User toDomain(UserRequestDTO dto);

    User toDomainUpdate(UserUpdateRequestDTO dto);

    List<UserResponseDTO> toDTOList(List<User> users);
}