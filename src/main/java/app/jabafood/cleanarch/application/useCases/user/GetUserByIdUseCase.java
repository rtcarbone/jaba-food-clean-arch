package app.jabafood.cleanarch.application.useCases.user;

import app.jabafood.cleanarch.domain.entities.User;
import app.jabafood.cleanarch.domain.gateways.IUserGateway;

import java.util.UUID;

public class GetUserByIdUseCase {
    private final IUserGateway userGateway;

    public GetUserByIdUseCase(IUserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public User execute(UUID id) {
        return userGateway.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}

