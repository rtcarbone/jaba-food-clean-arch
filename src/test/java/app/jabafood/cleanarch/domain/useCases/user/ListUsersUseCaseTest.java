package app.jabafood.cleanarch.domain.useCases.user;

import app.jabafood.cleanarch.domain.entities.User;
import app.jabafood.cleanarch.domain.enums.UserType;
import app.jabafood.cleanarch.domain.gateways.IUserGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListUsersUseCaseTest {

    private static final User USER_1 = new User(UUID.randomUUID(), "User 1", "user1@example.com", "user1login", "password", UserType.CUSTOMER, null, null);
    private static final User USER_2 = new User(UUID.randomUUID(), "User 2", "user2@example.com", "user2login", "password", UserType.CUSTOMER, null, null);

    @Mock
    private IUserGateway userGateway;

    @InjectMocks
    private ListUsersUseCase listUsersUseCase;

    @Test
    void shouldReturnListOfUsers() {
        // Arrange
        when(userGateway.findAll()).thenReturn(List.of(USER_1, USER_2));

        // Act
        List<User> result = listUsersUseCase.execute();

        // Assert
        assertEquals(2, result.size(), "The size of the user list should be 2");
        assertEquals(USER_1, result.get(0), "The first user in the list should be user1");
        assertEquals(USER_2, result.get(1), "The second user in the list should be user2");
    }

    @Test
    void shouldReturnEmptyListWhenNoUsersFound() {
        // Arrange
        when(userGateway.findAll()).thenReturn(List.of());

        // Act
        List<User> result = listUsersUseCase.execute();

        // Assert
        assertEquals(0, result.size(), "The user list should be empty");
    }
}
