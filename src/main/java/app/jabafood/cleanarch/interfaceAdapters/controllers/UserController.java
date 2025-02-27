package app.jabafood.cleanarch.interfaceAdapters.controllers;

import app.jabafood.cleanarch.application.useCases.user.*;
import app.jabafood.cleanarch.interfaceAdapters.dto.UserRequestDTO;
import app.jabafood.cleanarch.interfaceAdapters.mappers.UserMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final CreateUserUseCase createUserUseCase;
    private final GetUserByIdUseCase getUserByIdUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final ListUsersUseCase listUsersUseCase;
    private final UserMapper userMapper;

    public UserController(CreateUserUseCase createUserUseCase,
                          GetUserByIdUseCase getUserByIdUseCase,
                          UpdateUserUseCase updateUserUseCase,
                          DeleteUserUseCase deleteUserUseCase,
                          ListUsersUseCase listUsersUseCase,
                          UserMapper userMapper) {
        this.createUserUseCase = createUserUseCase;
        this.getUserByIdUseCase = getUserByIdUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
        this.listUsersUseCase = listUsersUseCase;
        this.userMapper = userMapper;
    }

    @PostMapping
    public ResponseEntity<UserRequestDTO> create(@RequestBody UserRequestDTO userRequestDTO) {
        var user = userMapper.toDomain(userRequestDTO);
        var createdUser = createUserUseCase.execute(user);
        return ResponseEntity.ok(userMapper.toDTO(createdUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserRequestDTO> findById(@PathVariable UUID id) {
        var user = getUserByIdUseCase.execute(id);
        return ResponseEntity.ok(userMapper.toDTO(user));
    }

    @GetMapping
    public ResponseEntity<List<UserRequestDTO>> listAll() {
        return ResponseEntity.ok(userMapper.toDTOList(listUsersUseCase.execute()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserRequestDTO> update(@PathVariable UUID id, @RequestBody UserRequestDTO userRequestDTO) {
        var user = userMapper.toDomain(userRequestDTO);
        var updatedUser = updateUserUseCase.execute(id, user);
        return ResponseEntity.ok(userMapper.toDTO(updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteUserUseCase.execute(id);
        return ResponseEntity.noContent()
                .build();
    }

}
