package app.jabafood.cleanarch.application.useCases.user;

import app.jabafood.cleanarch.domain.entities.User;
import app.jabafood.cleanarch.domain.entities.UserPassword;
import app.jabafood.cleanarch.domain.exceptions.InvalidPasswordException;
import app.jabafood.cleanarch.domain.exceptions.UserNotFoundException;
import app.jabafood.cleanarch.domain.gateways.IUserGateway;

import java.util.UUID;

public class UpdateUserPasswordUseCase {

  private final IUserGateway userGateway;

  public UpdateUserPasswordUseCase(IUserGateway userGateway) {
    this.userGateway = userGateway;
  }

  public User execute(UUID id, UserPassword userPassword) {
    var user = userGateway.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id));

    if (!user.getPassword().equals(userPassword.getOldPassword())) {
      throw new InvalidPasswordException("The old password is invalid");
    }

    user.setPassword(userPassword.getNewPassword());
    return userGateway.save(user);
  }
}
