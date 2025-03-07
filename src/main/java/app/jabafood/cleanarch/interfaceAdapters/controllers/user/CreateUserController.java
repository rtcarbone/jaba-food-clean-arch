package app.jabafood.cleanarch.interfaceAdapters.controllers.user;

import app.jabafood.cleanarch.application.useCases.user.CreateUserUseCase;
import app.jabafood.cleanarch.interfaceAdapters.dto.UserRequestDTO;
import app.jabafood.cleanarch.interfaceAdapters.dto.UserResponseDTO;
import app.jabafood.cleanarch.interfaceAdapters.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users/create")
@RequiredArgsConstructor
public class CreateUserController {
    private final CreateUserUseCase createUserUseCase;
    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@RequestBody UserRequestDTO userRequestDTO) {
        var user = userMapper.toDomain(userRequestDTO);
        var createdUser = createUserUseCase.execute(user);
        return ResponseEntity.ok(userMapper.toDTO(createdUser));
    }
}
