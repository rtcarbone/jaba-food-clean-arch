package app.jabafood.cleanarch.application.controllers.user;

import app.jabafood.cleanarch.application.dto.UserUpdatePasswordRequestDTO;
import app.jabafood.cleanarch.application.mappers.UserPasswordMapper;
import app.jabafood.cleanarch.domain.useCases.user.UpdateUserPasswordUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/users/{id}/update-password")
@RequiredArgsConstructor
@Tag(name = "User", description = "User Management API")
public class UpdateUserPasswordController {
    private final UpdateUserPasswordUseCase updateUserPasswordUseCase;
    private final UserPasswordMapper userPasswordMapper;

    @Operation(summary = "Update user password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User password updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PatchMapping
    public ResponseEntity<Void> updatePassword(@PathVariable UUID id, @RequestBody UserUpdatePasswordRequestDTO userUpdatePasswordRequestDTO) {
        var userPassword = userPasswordMapper.toValueObject(userUpdatePasswordRequestDTO);
        updateUserPasswordUseCase.execute(id, userPassword);
        return ResponseEntity.noContent()
                .build();
    }
}
