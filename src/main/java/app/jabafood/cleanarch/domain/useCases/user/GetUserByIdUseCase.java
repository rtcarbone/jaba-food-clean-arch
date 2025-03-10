package app.jabafood.cleanarch.domain.useCases.user;

import app.jabafood.cleanarch.domain.entities.User;
import app.jabafood.cleanarch.domain.exceptions.UserNotFoundException;
import app.jabafood.cleanarch.domain.gateways.IUserGateway;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class GetUserByIdUseCase {
    private final IUserGateway userGateway;

    public User execute(UUID id) {
        return userGateway.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}

