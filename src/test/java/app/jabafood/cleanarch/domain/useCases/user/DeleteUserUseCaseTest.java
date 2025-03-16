package app.jabafood.cleanarch.domain.useCases.user;

import app.jabafood.cleanarch.domain.entities.User;
import app.jabafood.cleanarch.domain.enums.UserType;
import app.jabafood.cleanarch.domain.exceptions.UserNotFoundException;
import app.jabafood.cleanarch.domain.gateways.IUserGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteUserUseCaseTest {

    @Mock
    private IUserGateway userGateway;

    @InjectMocks
    private DeleteUserUseCase deleteUserUseCase;

    private UUID userId;
    private User existingUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        userId = UUID.randomUUID();
        existingUser = new User(userId, "John Doe", "john.doe@example.com", "johndoe", "password", UserType.CUSTOMER, null, null);
    }

    @Test
    void shouldDeleteUserSuccessfully() {

        when(userGateway.findById(userId)).thenReturn(Optional.of(existingUser));

        deleteUserUseCase.execute(userId);
        verify(userGateway, times(1)).delete(userId);
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenUserDoesNotExist() {

        when(userGateway.findById(userId)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> {
            deleteUserUseCase.execute(userId);
        });
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenUserIdDoesNotMatch() {
        UUID wrongUserId = UUID.randomUUID();
        User differentUser = new User(wrongUserId, "Jane Doe", "jane.doe@example.com", "janedoe", "password", UserType.CUSTOMER, null, null);
        when(userGateway.findById(userId)).thenReturn(Optional.of(differentUser));


        assertThrows(UserNotFoundException.class, () -> {
            deleteUserUseCase.execute(userId);
        });
    }
}
