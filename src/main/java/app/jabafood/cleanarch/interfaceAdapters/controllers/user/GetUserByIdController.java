package app.jabafood.cleanarch.interfaceAdapters.controllers.user;

import app.jabafood.cleanarch.application.useCases.user.GetUserByIdUseCase;
import app.jabafood.cleanarch.interfaceAdapters.dto.UserRequestDTO;
import app.jabafood.cleanarch.interfaceAdapters.mappers.UserMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/users")
public class GetUserByIdController {

    private final GetUserByIdUseCase getUserByIdUseCase;
    private final UserMapper userMapper;

    public GetUserByIdController(GetUserByIdUseCase getUserByIdUseCase, UserMapper userMapper) {
        this.getUserByIdUseCase = getUserByIdUseCase;
        this.userMapper = userMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserRequestDTO> findById(@PathVariable UUID id) {
        var user = getUserByIdUseCase.execute(id);
        return ResponseEntity.ok(userMapper.toDTO(user));
    }

}
