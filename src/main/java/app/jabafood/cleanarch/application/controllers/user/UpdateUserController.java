package app.jabafood.cleanarch.application.controllers.user;

import app.jabafood.cleanarch.application.dto.UserResponseDTO;
import app.jabafood.cleanarch.application.dto.UserUpdateRequestDTO;
import app.jabafood.cleanarch.application.mappers.UserMapper;
import app.jabafood.cleanarch.domain.useCases.user.UpdateUserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/users/{id}/update")
@RequiredArgsConstructor
@Tag(name = "User", description = "User Management API")
public class UpdateUserController {
    private final UpdateUserUseCase updateUserUseCase;
    private final UserMapper userMapper;

    @Operation(summary = "Update user details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping
    public ResponseEntity<UserResponseDTO> update(@PathVariable UUID id, @RequestBody UserUpdateRequestDTO userRequestDTO) {
        var user = userMapper.toDomainUpdate(userRequestDTO);
        var updatedUser = updateUserUseCase.execute(id, user);
        return ResponseEntity.ok(userMapper.toDTO(updatedUser));
    }
}