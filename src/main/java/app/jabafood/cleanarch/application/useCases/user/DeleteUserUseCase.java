package app.jabafood.cleanarch.application.useCases.user;

import app.jabafood.cleanarch.domain.repositories.UserRepository;

import java.util.UUID;

public class DeleteUserUseCase {
    private final UserRepository userRepository;

    public DeleteUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(UUID id) {
        userRepository.delete(id);
    }
}
