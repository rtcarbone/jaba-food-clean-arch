package app.jabafood.cleanarch.domain.useCases.user;

import app.jabafood.cleanarch.domain.entities.Address;
import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.domain.entities.User;
import app.jabafood.cleanarch.domain.enums.CuisineType;
import app.jabafood.cleanarch.domain.enums.UserType;
import app.jabafood.cleanarch.domain.exceptions.EmailAlreadyInUseException;
import app.jabafood.cleanarch.domain.exceptions.UpdateUserException;
import app.jabafood.cleanarch.domain.exceptions.UserNotFoundException;
import app.jabafood.cleanarch.domain.gateways.IRestaurantGateway;
import app.jabafood.cleanarch.domain.gateways.IUserGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateUserUseCaseTest {

    private static final UUID USER_ID = UUID.randomUUID();
    private static final String OLD_EMAIL = "john@example.com";
    private static final String UPDATED_EMAIL = "john.updated@example.com";
    private static final String OLD_NAME = "John Doe";
    private static final String UPDATED_NAME = "John Updated";
    private static final String OLD_LOGIN = "johndoe";
    private static final String UPDATED_LOGIN = "johnupdated";
    private static final String OLD_PASSWORD = "password123";
    private static final String UPDATED_PASSWORD = "newpassword123";

    @Mock
    private IUserGateway userGateway;

    @Mock
    private IRestaurantGateway restaurantGateway;

    @Mock
    private Address address;

    @InjectMocks
    private UpdateUserUseCase updateUserUseCase;

    private User existingUser;
    private User updatedUserData;

    @BeforeEach
    void setUp() {
        existingUser = new User(USER_ID, OLD_NAME, OLD_EMAIL, OLD_LOGIN, OLD_PASSWORD, UserType.CUSTOMER, LocalDateTime.now(), address);
        updatedUserData = new User(USER_ID, UPDATED_NAME, UPDATED_EMAIL, UPDATED_LOGIN, UPDATED_PASSWORD, UserType.CUSTOMER, LocalDateTime.now(), address);
    }

    @Test
    void shouldUpdateUserSuccessfully() {
        // Arrange
        when(userGateway.findById(USER_ID)).thenReturn(Optional.of(existingUser));
        when(userGateway.findByEmail(UPDATED_EMAIL)).thenReturn(Optional.empty());
        when(userGateway.save(any(User.class))).thenReturn(updatedUserData);

        // Act
        User result = updateUserUseCase.execute(USER_ID, updatedUserData);

        // Assert
        assertEquals(UPDATED_NAME, result.getName());
        assertEquals(UPDATED_EMAIL, result.getEmail());
        assertEquals(UPDATED_LOGIN, result.getLogin());

        verify(userGateway).findById(USER_ID);
        verify(userGateway).findByEmail(UPDATED_EMAIL);
        verify(userGateway).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        // Arrange
        when(userGateway.findById(USER_ID)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> updateUserUseCase.execute(USER_ID, updatedUserData))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("User with ID '" + USER_ID + "' not found.");

        verify(userGateway).findById(USER_ID);
        verify(userGateway, never()).findByEmail(anyString());
        verify(userGateway, never()).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenEmailIsAlreadyInUse() {
        // Arrange
        when(userGateway.findById(USER_ID)).thenReturn(Optional.of(existingUser));
        when(userGateway.findByEmail(UPDATED_EMAIL)).thenReturn(Optional.of(existingUser));

        // Act & Assert
        assertThatThrownBy(() -> updateUserUseCase.execute(USER_ID, updatedUserData))
                .isInstanceOf(EmailAlreadyInUseException.class)
                .hasMessage("Email already in use");

        verify(userGateway).findById(USER_ID);
        verify(userGateway).findByEmail(UPDATED_EMAIL);
        verify(userGateway, never()).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenUserTypeCannotBeChanged() {
        // Arrange
        User existingUser = new User(
                USER_ID, OLD_NAME, OLD_EMAIL, OLD_LOGIN, OLD_PASSWORD, UserType.RESTAURANT_OWNER, LocalDateTime.now(), address
        );

        Restaurant restaurant = new Restaurant(
                UUID.randomUUID(), "Restaurant", address, CuisineType.BURGER,
                LocalTime.of(10, 0), LocalTime.of(22, 0), existingUser
        );

        when(userGateway.findById(USER_ID)).thenReturn(Optional.of(existingUser));
        when(restaurantGateway.findByOwnerId(USER_ID)).thenReturn(List.of(restaurant));

        User updatedUser = new User(
                USER_ID, UPDATED_NAME, UPDATED_EMAIL, UPDATED_LOGIN, UPDATED_PASSWORD, UserType.CUSTOMER, LocalDateTime.now(), address
        );

        // Act & Assert
        UpdateUserException exception = assertThrows(UpdateUserException.class, () ->
                updateUserUseCase.execute(USER_ID, updatedUser));

        assertEquals("User type cannot be changed because the user is associated with one or more restaurants.", exception.getMessage());

        verify(userGateway).findById(USER_ID);
        verify(restaurantGateway).findByOwnerId(USER_ID);
        verify(userGateway, never()).save(any(User.class));
    }
}
