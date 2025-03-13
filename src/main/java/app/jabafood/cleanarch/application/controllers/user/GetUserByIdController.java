package app.jabafood.cleanarch.application.controllers.user;

import app.jabafood.cleanarch.domain.useCases.user.GetUserByIdUseCase;
import app.jabafood.cleanarch.application.dto.UserResponseDTO;
import app.jabafood.cleanarch.application.mappers.UserMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/users/{id}")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Get User By ID", description = "Get User By ID API")
public class GetUserByIdController {
    private final GetUserByIdUseCase getUserByIdUseCase;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<UserResponseDTO> findById(@PathVariable UUID id) {
        var user = getUserByIdUseCase.execute(id);
        return ResponseEntity.ok(userMapper.toDTO(user));
    }
}