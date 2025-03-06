package app.jabafood.cleanarch.application.useCases.user;

import app.jabafood.cleanarch.domain.entities.User;
import app.jabafood.cleanarch.domain.exceptions.EmailAlreadyInUseException;
import app.jabafood.cleanarch.domain.exceptions.LoginAlreadyInUseException;
import app.jabafood.cleanarch.domain.exceptions.UserAlreadyExistsException;
import app.jabafood.cleanarch.domain.gateways.IUserGateway;

import java.util.Optional;

public class CreateUserUseCase {
    private final IUserGateway userGateway;

    public CreateUserUseCase(IUserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public User execute(User user) {
        Optional<User> existingEmail = userGateway.findByEmail(user.getEmail());
        Optional<User> existingLogin = userGateway.findByLogin(user.getLogin());

        existingLogin
                .ifPresent(u -> {
                    throw new LoginAlreadyInUseException("Login already in use");
                });
        if (user.getId() != null) {
            Optional<User> existingId = userGateway.findById(user.getId());
            existingId
                    .ifPresent(u -> {
                        throw new UserAlreadyExistsException("User already exists");
                    });
        }

        existingEmail
                .ifPresent(u -> {
                    throw new EmailAlreadyInUseException("Email already in use");
                });

        User newUser = user.copyWith(
                user.getName(),
                user.getEmail(),
                user.getLogin(),
                user.getPassword(),
                user.getUserType(),
                user.getAddress());

        newUser.validate();

        return userGateway.save(newUser);
    }
}