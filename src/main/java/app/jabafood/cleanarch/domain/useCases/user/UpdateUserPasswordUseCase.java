package app.jabafood.cleanarch.domain.useCases.user;

import app.jabafood.cleanarch.domain.entities.User;
import app.jabafood.cleanarch.domain.exceptions.InvalidPasswordException;
import app.jabafood.cleanarch.domain.exceptions.UserNotFoundException;
import app.jabafood.cleanarch.domain.gateways.IUserGateway;
import app.jabafood.cleanarch.domain.valueObjects.UserPassword;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class UpdateUserPasswordUseCase {
    private final IUserGateway userGateway;

    public User execute(UUID id, UserPassword userPassword) {
        var user = userGateway.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        userPassword.validate();

        if (!user.getPassword()
                .equals(userPassword.getOldPassword())) {
            throw new InvalidPasswordException("The old password is invalid");
        }

        User updatedUser = user.copyWith(
                user.getName(),
                user.getEmail(),
                user.getLogin(),
                userPassword.getNewPassword(),
                user.getUserType(),
                user.getAddress()
        );

        updatedUser.validate();

        return userGateway.save(updatedUser);
    }
}
