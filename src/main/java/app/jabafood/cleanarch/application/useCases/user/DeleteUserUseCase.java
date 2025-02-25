package app.jabafood.cleanarch.application.useCases.user;

import app.jabafood.cleanarch.domain.gateways.IUserGateway;

import java.util.UUID;

public class DeleteUserUseCase {
    private final IUserGateway userGateway;

    public DeleteUserUseCase(IUserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public void execute(UUID id) {
        userGateway.delete(id);
    }
}
