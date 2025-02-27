package app.jabafood.cleanarch.interfaceAdapters.mappers;

import app.jabafood.cleanarch.domain.entities.User;
import app.jabafood.cleanarch.interfaceAdapters.dto.UserRequestDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserRequestDTO toDTO(User user);

    User toDomain(UserRequestDTO dto);

    List<UserRequestDTO> toDTOList(List<User> users);
}
