package app.jabafood.cleanarch.application.useCases.user;

import app.jabafood.cleanarch.domain.entities.User;
import app.jabafood.cleanarch.domain.gateways.IUserGateway;

import java.util.UUID;

public class UpdateUserUseCase {
    private final IUserGateway userGateway;

    public UpdateUserUseCase(IUserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public User execute(UUID id, User updatedData) {
        User existingUser = userGateway.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        User updatedUser = existingUser.copyWith(
                updatedData.getName(),
                updatedData.getEmail(),
                updatedData.getPassword(),
                updatedData.getAddress()
        );

        return userGateway.save(updatedUser);
    }
}
