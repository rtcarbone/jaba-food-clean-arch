package app.jabafood.cleanarch.domain.useCases.user;

import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.domain.entities.User;
import app.jabafood.cleanarch.domain.enums.UserType;
import app.jabafood.cleanarch.domain.exceptions.EmailAlreadyInUseException;
import app.jabafood.cleanarch.domain.exceptions.UpdateUserException;
import app.jabafood.cleanarch.domain.exceptions.UserNotFoundException;
import app.jabafood.cleanarch.domain.gateways.IRestaurantGateway;
import app.jabafood.cleanarch.domain.gateways.IUserGateway;
import app.jabafood.cleanarch.domain.validations.EmailFormatValidation;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class UpdateUserUseCase {
    private final IUserGateway userGateway;
    private final IRestaurantGateway restaurantGateway;

    public User execute(UUID id, User updatedData) {
        User existingUser = userGateway.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        validateEmailChange(existingUser, updatedData.getEmail());

        List<Restaurant> restaurants = restaurantGateway.findByOwnerId(existingUser.getId());

        if (UserType.RESTAURANT_OWNER.equals(existingUser.getUserType()) && !UserType.RESTAURANT_OWNER.equals(updatedData.getUserType()) && !restaurants.isEmpty()) {
            throw new UpdateUserException("User type cannot be changed because the user is associated with one or more restaurants.");
        }

        User updatedUser = existingUser.copyWith(
                updatedData.getName(),
                updatedData.getEmail(),
                updatedData.getLogin(),
                existingUser.getPassword(),
                updatedData.getUserType(),
                updatedData.getAddress()
        );

        updatedUser.validate();

        return userGateway.save(updatedUser);
    }

    private void validateEmailChange(User existingUser, String newEmail) {
        if (newEmail == null || newEmail.equals(existingUser.getEmail())) {
            return;
        }

        EmailFormatValidation.validate(newEmail);

        userGateway.findByEmail(newEmail)
                .ifPresent(user -> {
                    throw new EmailAlreadyInUseException("Email already in use");
                });
    }
}
