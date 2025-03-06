package app.jabafood.cleanarch.interfaceAdapters.controllers.user;

import app.jabafood.cleanarch.application.useCases.user.DeleteUserUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/users/delete")
public class DeleteUserController {

  private final DeleteUserUseCase deleteUserUseCase;

  public DeleteUserController(DeleteUserUseCase deleteUserUseCase) {
    this.deleteUserUseCase = deleteUserUseCase;
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    deleteUserUseCase.execute(id);
    return ResponseEntity.noContent()
            .build();
  }
}
