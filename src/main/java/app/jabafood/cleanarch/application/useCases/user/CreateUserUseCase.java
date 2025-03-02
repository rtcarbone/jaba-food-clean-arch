package app.jabafood.cleanarch.application.useCases.user;

import app.jabafood.cleanarch.domain.entities.User;
import app.jabafood.cleanarch.domain.exceptions.EmailAlreadyInUseException;
import app.jabafood.cleanarch.domain.exceptions.LoginAlreadyInUseException;
import app.jabafood.cleanarch.domain.exceptions.UserAlreadyExistsException;
import app.jabafood.cleanarch.domain.gateways.IUserGateway;

public class CreateUserUseCase {
    private final IUserGateway userGateway;

    public CreateUserUseCase(IUserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public User execute(User user) {
//        userGateway.findByLogin(user.getLogin())
//                .ifPresent(u -> {
//                    throw new LoginAlreadyInUseException("Login already in use");
//                });
//        userGateway.findById(user.getId())
//                .ifPresent(u -> {
//                    throw new UserAlreadyExistsException("User already exists");
//                });
//
//        userGateway.findByEmail(user.getEmail())
//                .ifPresent(u -> {
//                    throw new EmailAlreadyInUseException("Email already in use");
//                });
//
//        User newUser = user.copyWith(user.getName(), user.getEmail(), user.getLogin(), user.getPassword(), user.getAddress());
//        newUser.validate();

        return userGateway.save(user);
    }
}