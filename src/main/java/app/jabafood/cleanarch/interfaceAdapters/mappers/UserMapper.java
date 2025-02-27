package app.jabafood.cleanarch.interfaceAdapters.mappers;

import app.jabafood.cleanarch.domain.entities.User;
import app.jabafood.cleanarch.interfaceAdapters.dto.UserDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDTO(User user);

    User toDomain(UserDTO dto);

    List<UserDTO> toDTOList(List<User> users);
}
