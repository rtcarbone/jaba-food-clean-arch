package app.jabafood.cleanarch.domain.useCases.user;

import app.jabafood.cleanarch.domain.entities.Address;
import app.jabafood.cleanarch.domain.entities.User;
import app.jabafood.cleanarch.domain.enums.UserType;
import app.jabafood.cleanarch.domain.exceptions.InvalidPasswordException;
import app.jabafood.cleanarch.domain.exceptions.UserNotFoundException;
import app.jabafood.cleanarch.domain.gateways.IUserGateway;
import app.jabafood.cleanarch.domain.valueObjects.UserPassword;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateUserPasswordUseCaseTest {

    private static final UUID USER_ID = UUID.randomUUID();
    private static final String OLD_PASSWORD = "oldPassword";
    private static final String NEW_PASSWORD = "newPassword";
    private static final String WRONG_OLD_PASSWORD = "wrongOldPassword";

    @Mock
    private IUserGateway userGateway;

    @Mock
    private Address address;

    @InjectMocks
    private UpdateUserPasswordUseCase updateUserPasswordUseCase;

    private User existingUser;
    private UserPassword userPassword;

    @BeforeEach
    void setUp() {
        existingUser = new User(USER_ID, "John Doe", "john.doe@example.com", "johndoe", OLD_PASSWORD, UserType.CUSTOMER, null, address);
        userPassword = new UserPassword(OLD_PASSWORD, NEW_PASSWORD, NEW_PASSWORD);
    }

    @Test
    void shouldUpdateUserPasswordSuccessfully() {
        // Arrange
        User updatedUser = new User(USER_ID, "John Doe", "john.doe@example.com", "johndoe", NEW_PASSWORD, UserType.CUSTOMER, null, address);

        when(userGateway.findById(USER_ID)).thenReturn(Optional.of(existingUser));
        when(userGateway.save(any(User.class))).thenReturn(updatedUser);

        // Act
        User result = updateUserPasswordUseCase.execute(USER_ID, userPassword);

        // Assert
        assertNotNull(result);
        assertEquals(NEW_PASSWORD, result.getPassword());

        verify(userGateway, times(1)).findById(USER_ID);
        verify(userGateway, times(1)).save(any(User.class));
    }

    @Test
    void shouldThrowInvalidPasswordExceptionWhenOldPasswordIsIncorrect() {
        // Arrange
        when(userGateway.findById(USER_ID)).thenReturn(Optional.of(existingUser));

        UserPassword invalidPassword = new UserPassword(WRONG_OLD_PASSWORD, NEW_PASSWORD, NEW_PASSWORD);

        // Act & Assert
        InvalidPasswordException exception = assertThrows(InvalidPasswordException.class, () ->
                updateUserPasswordUseCase.execute(USER_ID, invalidPassword));

        assertEquals("The old password is invalid", exception.getMessage());

        verify(userGateway, times(1)).findById(USER_ID);
        verify(userGateway, never()).save(any(User.class));
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenUserDoesNotExist() {
        // Arrange
        when(userGateway.findById(USER_ID)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> updateUserPasswordUseCase.execute(USER_ID, userPassword));

        verify(userGateway, times(1)).findById(USER_ID);
        verify(userGateway, never()).save(any(User.class));
    }
}
