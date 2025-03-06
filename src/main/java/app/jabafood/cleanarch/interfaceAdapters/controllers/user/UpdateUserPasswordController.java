package app.jabafood.cleanarch.interfaceAdapters.controllers.user;

import app.jabafood.cleanarch.application.useCases.user.UpdateUserPasswordUseCase;
import app.jabafood.cleanarch.domain.gateways.IUserGateway;
import app.jabafood.cleanarch.interfaceAdapters.dto.UserUpdatePasswordRequestDTO;
import app.jabafood.cleanarch.interfaceAdapters.mappers.UserMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/users/{id}/update-password")

public class UpdateUserPasswordController {

  private IUserGateway iUserGateway;
  private  final UpdateUserPasswordUseCase updateUserPasswordUseCase;

  private final UserMapper userMapper;

public UpdateUserPasswordController(UpdateUserPasswordUseCase updateUserPasswordUseCase, UserMapper userMapper) {
    this.updateUserPasswordUseCase = updateUserPasswordUseCase;
    this.userMapper = userMapper;
  }

  @PatchMapping
  public ResponseEntity<Void> updatePassword(@PathVariable UUID id, @RequestBody UserUpdatePasswordRequestDTO userUpdatePasswordRequestDTO) {
    var userPassword = userMapper.toDomainPasswordUpdate(userUpdatePasswordRequestDTO);
    updateUserPasswordUseCase.execute(id, userPassword);
    return ResponseEntity.noContent().build();
  }

}
