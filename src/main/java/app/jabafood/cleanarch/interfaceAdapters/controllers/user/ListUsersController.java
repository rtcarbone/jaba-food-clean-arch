package app.jabafood.cleanarch.interfaceAdapters.controllers.user;

import app.jabafood.cleanarch.application.useCases.user.ListUsersUseCase;
import app.jabafood.cleanarch.interfaceAdapters.dto.UserRequestDTO;
import app.jabafood.cleanarch.interfaceAdapters.mappers.UserMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/users/list")
public class ListUsersController {

    private final ListUsersUseCase listUsersUseCase;
    private final UserMapper userMapper;

    public ListUsersController(ListUsersUseCase listUsersUseCase, UserMapper userMapper) {
        this.listUsersUseCase = listUsersUseCase;
        this.userMapper = userMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserRequestDTO>> list() {
        return ResponseEntity.ok(userMapper.toDTOList(listUsersUseCase.execute()));
    }

}
