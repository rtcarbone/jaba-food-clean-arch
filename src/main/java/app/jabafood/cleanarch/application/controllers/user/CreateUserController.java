package app.jabafood.cleanarch.application.controllers.user;

import app.jabafood.cleanarch.application.dto.UserRequestDTO;
import app.jabafood.cleanarch.application.dto.UserResponseDTO;
import app.jabafood.cleanarch.application.mappers.UserMapper;
import app.jabafood.cleanarch.domain.useCases.user.CreateUserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users/create")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Create User", description = "Create User API")
public class CreateUserController {
    private final CreateUserUseCase createUserUseCase;
    private final UserMapper userMapper;

    @Operation(summary = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@RequestBody UserRequestDTO userRequestDTO) {
        var user = userMapper.toDomain(userRequestDTO);
        var createdUser = createUserUseCase.execute(user);
        return ResponseEntity.ok(userMapper.toDTO(createdUser));
    }
}
