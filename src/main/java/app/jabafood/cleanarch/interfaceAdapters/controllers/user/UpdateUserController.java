package app.jabafood.cleanarch.interfaceAdapters.controllers.user;

import app.jabafood.cleanarch.application.useCases.user.UpdateUserUseCase;
import app.jabafood.cleanarch.interfaceAdapters.dto.UserRequestDTO;
import app.jabafood.cleanarch.interfaceAdapters.mappers.UserMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/users/update")
public class UpdateUserController {

    private final UpdateUserUseCase updateUserUseCase;
    private final UserMapper userMapper;

    public UpdateUserController(UpdateUserUseCase updateUserUseCase, UserMapper userMapper) {
        this.updateUserUseCase = updateUserUseCase;
        this.userMapper = userMapper;
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserRequestDTO> update(@PathVariable UUID id, @RequestBody UserRequestDTO userRequestDTO) {
        var user = userMapper.toDomain(userRequestDTO);
        var updatedUser = updateUserUseCase.execute(id, user);
        return ResponseEntity.ok(userMapper.toDTO(updatedUser));
    }

}
