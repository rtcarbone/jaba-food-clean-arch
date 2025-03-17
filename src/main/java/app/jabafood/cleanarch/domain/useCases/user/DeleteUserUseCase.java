package app.jabafood.cleanarch.domain.useCases.user;

import app.jabafood.cleanarch.domain.exceptions.UserNotFoundException;
import app.jabafood.cleanarch.domain.gateways.IUserGateway;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class DeleteUserUseCase {

    private final IUserGateway userGateway;

    public void execute(UUID id) {
        userGateway.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        userGateway.delete(id);
    }
}
