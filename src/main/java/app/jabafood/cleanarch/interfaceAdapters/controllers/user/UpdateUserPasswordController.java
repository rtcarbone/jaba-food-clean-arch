package app.jabafood.cleanarch.interfaceAdapters.controllers.user;

import app.jabafood.cleanarch.application.useCases.user.UpdateUserPasswordUseCase;
import app.jabafood.cleanarch.interfaceAdapters.dto.UserUpdatePasswordRequestDTO;
import app.jabafood.cleanarch.interfaceAdapters.mappers.UserPasswordMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/users/{id}/update-password")

public class UpdateUserPasswordController {

    private final UpdateUserPasswordUseCase updateUserPasswordUseCase;
    private final UserPasswordMapper userPasswordMapper;

    public UpdateUserPasswordController(UpdateUserPasswordUseCase updateUserPasswordUseCase, UserPasswordMapper userPasswordMapper) {
        this.updateUserPasswordUseCase = updateUserPasswordUseCase;
        this.userPasswordMapper = userPasswordMapper;
    }

    @PatchMapping
    public ResponseEntity<Void> updatePassword(@PathVariable UUID id, @RequestBody UserUpdatePasswordRequestDTO userUpdatePasswordRequestDTO) {
        var userPassword = userPasswordMapper.toValueObject(userUpdatePasswordRequestDTO);
        updateUserPasswordUseCase.execute(id, userPassword);
        return ResponseEntity.noContent()
                .build();
    }

}
