package app.jabafood.cleanarch.application.useCases.user;

import app.jabafood.cleanarch.domain.exceptions.UserNotFoundException;
import app.jabafood.cleanarch.domain.gateways.IUserGateway;

import java.util.Objects;
import java.util.UUID;

public class DeleteUserUseCase {
    private final IUserGateway userGateway;

    public DeleteUserUseCase(IUserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public void execute(UUID id) {

        if (userGateway.findById(id)
                    .isEmpty() || !Objects.equals(id, userGateway.findById(id)
                .get()
                .getId())) {
            throw new UserNotFoundException(id);
        }

        userGateway.delete(id);
    }
}
