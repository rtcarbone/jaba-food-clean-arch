package app.jabafood.cleanarch.application.useCases.user;

import app.jabafood.cleanarch.domain.entities.User;
import app.jabafood.cleanarch.domain.gateways.IUserGateway;

import java.util.List;

public class ListUsersUseCase {
    private final IUserGateway userGateway;

    public ListUsersUseCase(IUserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public List<User> execute() {
        return userGateway.findAll();
    }
}
