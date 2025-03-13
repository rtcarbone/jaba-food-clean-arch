package app.jabafood.cleanarch.application.controllers.user;

import app.jabafood.cleanarch.domain.useCases.user.DeleteUserUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/users/{id}/delete")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Delete User", description = "Delete User API")
public class DeleteUserController {
    private final DeleteUserUseCase deleteUserUseCase;

    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteUserUseCase.execute(id);
        return ResponseEntity.noContent()
                .build();
    }
}