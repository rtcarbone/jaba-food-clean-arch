package app.jabafood.cleanarch.application.controllers.user;

import app.jabafood.cleanarch.domain.useCases.user.ListUsersUseCase;
import app.jabafood.cleanarch.application.dto.UserResponseDTO;
import app.jabafood.cleanarch.application.mappers.UserMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/users/list")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "List Users", description = "List Users API")
public class ListUsersController {
    private final ListUsersUseCase listUsersUseCase;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> list() {
        return ResponseEntity.ok(userMapper.toDTOList(listUsersUseCase.execute()));
    }
}
