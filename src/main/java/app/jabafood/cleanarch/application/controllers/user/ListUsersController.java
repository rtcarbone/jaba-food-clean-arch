package app.jabafood.cleanarch.application.controllers.user;

import app.jabafood.cleanarch.application.dto.UserResponseDTO;
import app.jabafood.cleanarch.application.mappers.UserMapper;
import app.jabafood.cleanarch.domain.useCases.user.ListUsersUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/users/list")
@RequiredArgsConstructor
@Tag(name = "User", description = "User Management API")
public class ListUsersController {
    private final ListUsersUseCase listUsersUseCase;
    private final UserMapper userMapper;

    @Operation(summary = "List all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of users retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> list() {
        return ResponseEntity.ok(userMapper.toDTOList(listUsersUseCase.execute()));
    }
}
