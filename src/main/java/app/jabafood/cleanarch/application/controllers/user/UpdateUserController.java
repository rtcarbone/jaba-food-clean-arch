package app.jabafood.cleanarch.application.controllers.user;

import app.jabafood.cleanarch.domain.useCases.user.UpdateUserUseCase;
import app.jabafood.cleanarch.application.dto.UserResponseDTO;
import app.jabafood.cleanarch.application.dto.UserUpdateRequestDTO;
import app.jabafood.cleanarch.application.mappers.UserMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/users/{id}/update")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Update User", description = "Update User API")
public class UpdateUserController {
    private final UpdateUserUseCase updateUserUseCase;
    private final UserMapper userMapper;

    @PutMapping
    public ResponseEntity<UserResponseDTO> update(@PathVariable UUID id, @RequestBody UserUpdateRequestDTO userRequestDTO) {
        var user = userMapper.toDomainUpdate(userRequestDTO);
        var updatedUser = updateUserUseCase.execute(id, user);
        return ResponseEntity.ok(userMapper.toDTO(updatedUser));
    }
}