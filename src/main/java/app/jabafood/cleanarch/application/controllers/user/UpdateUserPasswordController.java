package app.jabafood.cleanarch.application.controllers.user;

import app.jabafood.cleanarch.domain.useCases.user.UpdateUserPasswordUseCase;
import app.jabafood.cleanarch.application.dto.UserUpdatePasswordRequestDTO;
import app.jabafood.cleanarch.application.mappers.UserPasswordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/users/{id}/update-password")
@RequiredArgsConstructor
public class UpdateUserPasswordController {
    private final UpdateUserPasswordUseCase updateUserPasswordUseCase;
    private final UserPasswordMapper userPasswordMapper;

    @PatchMapping
    public ResponseEntity<Void> updatePassword(@PathVariable UUID id, @RequestBody UserUpdatePasswordRequestDTO userUpdatePasswordRequestDTO) {
        var userPassword = userPasswordMapper.toValueObject(userUpdatePasswordRequestDTO);
        updateUserPasswordUseCase.execute(id, userPassword);
        return ResponseEntity.noContent()
                .build();
    }
}
