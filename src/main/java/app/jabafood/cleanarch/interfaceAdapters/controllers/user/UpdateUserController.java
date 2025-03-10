package app.jabafood.cleanarch.interfaceAdapters.controllers.user;

import app.jabafood.cleanarch.application.useCases.user.UpdateUserUseCase;
import app.jabafood.cleanarch.interfaceAdapters.dto.UserResponseDTO;
import app.jabafood.cleanarch.interfaceAdapters.dto.UserUpdateRequestDTO;
import app.jabafood.cleanarch.interfaceAdapters.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/users/{id}/update")
@RequiredArgsConstructor
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