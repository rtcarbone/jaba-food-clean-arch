package app.jabafood.cleanarch.domain.useCases.user;

import app.jabafood.cleanarch.domain.entities.User;
import app.jabafood.cleanarch.domain.exceptions.EmailAlreadyInUseException;
import app.jabafood.cleanarch.domain.exceptions.LoginAlreadyInUseException;
import app.jabafood.cleanarch.domain.exceptions.UserAlreadyExistsException;
import app.jabafood.cleanarch.domain.gateways.IUserGateway;
import app.jabafood.cleanarch.domain.validations.EmailFormatValidation;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class CreateUserUseCase {
    private final IUserGateway userGateway;

    public User execute(User user) {

        user.validate();

        EmailFormatValidation.validate(user.getEmail());

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

        return userGateway.save(newUser);
    }
}