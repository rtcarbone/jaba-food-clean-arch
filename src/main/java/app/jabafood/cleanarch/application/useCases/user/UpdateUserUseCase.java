package app.jabafood.cleanarch.application.useCases.user;

import app.jabafood.cleanarch.domain.entities.User;
import app.jabafood.cleanarch.domain.repositories.UserRepository;

import java.util.UUID;

public class UpdateUserUseCase {
    private final UserRepository userRepository;

    public UpdateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(UUID id, User updatedData) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        User updatedUser = new User(
                existingUser.getId(),
                updatedData.getName() != null ? updatedData.getName() : existingUser.getName(),
                updatedData.getEmail() != null ? updatedData.getEmail() : existingUser.getEmail(),
                existingUser.getUsername(),
                updatedData.getPassword() != null ? updatedData.getPassword() : existingUser.getPassword(),
                existingUser.getUserType(),
                updatedData.getAddress() != null ? updatedData.getAddress() : existingUser.getAddress()
        );

        return userRepository.save(updatedUser);
    }
}
