package app.jabafood.cleanarch.application.useCases.user;

import app.jabafood.cleanarch.domain.entities.User;
import app.jabafood.cleanarch.domain.exceptions.EmailAlreadyInUseException;
import app.jabafood.cleanarch.domain.exceptions.PasswordNotMatchException;
import app.jabafood.cleanarch.domain.exceptions.UserNotFoundException;
import app.jabafood.cleanarch.domain.gateways.IUserGateway;

import java.util.UUID;

public class UpdateUserUseCase {
  private final IUserGateway userGateway;

  public UpdateUserUseCase(IUserGateway userGateway) {
    this.userGateway = userGateway;
  }

  public User execute(UUID id, User updatedData) {
    User existingUser = userGateway.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id));

    if (updatedData.getEmail() != null && !updatedData.getEmail().equals(existingUser.getEmail())) {
      userGateway.findByEmail(updatedData.getEmail()).ifPresent(user -> {
        throw new EmailAlreadyInUseException("Email already in use");
      });
    }

    User updatedUser = existingUser.copyWith(
            updatedData.getName(),
            updatedData.getEmail(),
            updatedData.getPassword(),
            updatedData.getLogin(),
            updatedData.getUserType(),
            updatedData.getAddress()
    );

    return userGateway.save(updatedUser);
  }
}
