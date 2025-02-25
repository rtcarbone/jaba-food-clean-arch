package app.jabafood.cleanarch.application.useCases.user;

import app.jabafood.cleanarch.domain.entities.User;
import app.jabafood.cleanarch.domain.gateways.IUserGateway;

public class CreateUserUseCase {
    private final IUserGateway userGateway;

    public CreateUserUseCase(IUserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public User execute(User user) {
        return userGateway.save(user);
    }
}