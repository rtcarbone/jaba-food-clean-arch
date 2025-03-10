package app.jabafood.cleanarch.application.mappers;

import app.jabafood.cleanarch.domain.entities.User;
import app.jabafood.cleanarch.application.dto.UserRequestDTO;
import app.jabafood.cleanarch.application.dto.UserResponseDTO;
import app.jabafood.cleanarch.application.dto.UserUpdateRequestDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDTO toDTO(User user);

    User toDomain(UserRequestDTO dto);

    User toDomainUpdate(UserUpdateRequestDTO dto);

    List<UserResponseDTO> toDTOList(List<User> users);
}