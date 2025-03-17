package app.jabafood.cleanarch.domain.useCases.user;

import app.jabafood.cleanarch.domain.entities.User;
import app.jabafood.cleanarch.domain.enums.UserType;
import app.jabafood.cleanarch.domain.exceptions.UserNotFoundException;
import app.jabafood.cleanarch.domain.gateways.IUserGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteUserUseCaseTest {

    private static final UUID USER_ID = UUID.randomUUID();
    private static final String NAME = "John Doe";
    private static final String EMAIL = "john.doe@example.com";
    private static final String LOGIN = "johndoe";
    private static final String PASSWORD = "password";
    private static final UserType USER_TYPE = UserType.CUSTOMER;

    @Mock
    private IUserGateway userGateway;

    @InjectMocks
    private DeleteUserUseCase deleteUserUseCase;

    private User existingUser;

    @BeforeEach
    void setUp() {
        existingUser = new User(USER_ID, NAME, EMAIL, LOGIN, PASSWORD, USER_TYPE, null, null);
    }

    @Test
    void shouldDeleteUserSuccessfully() {
        // Arrange
        when(userGateway.findById(USER_ID)).thenReturn(Optional.of(existingUser));

        // Act
        deleteUserUseCase.execute(USER_ID);

        // Assert
        verify(userGateway, times(1)).findById(USER_ID);
        verify(userGateway, times(1)).delete(USER_ID);
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenUserDoesNotExist() {
        // Arrange
        when(userGateway.findById(USER_ID)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> deleteUserUseCase.execute(USER_ID));

        verify(userGateway, times(1)).findById(USER_ID);
        verify(userGateway, never()).delete(any(UUID.class));
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenUserIdDoesNotMatch() {
        // Arrange
        when(userGateway.findById(USER_ID)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> deleteUserUseCase.execute(USER_ID));

        verify(userGateway, times(1)).findById(USER_ID);
        verify(userGateway, never()).delete(any(UUID.class));
    }
}
