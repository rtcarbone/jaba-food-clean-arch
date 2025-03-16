package app.jabafood.cleanarch.domain.useCases.user;

import app.jabafood.cleanarch.domain.entities.User;
import app.jabafood.cleanarch.domain.enums.UserType;
import app.jabafood.cleanarch.domain.gateways.IUserGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ListUsersUseCaseTest {
    @Mock
    private IUserGateway userGateway;

    @InjectMocks
    private ListUsersUseCase listUsersUseCase;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
        user1 = new User(UUID.randomUUID(), "User 1", "user1@example.com", "user1login", "password", UserType.CUSTOMER, null, null);
        user2 = new User(UUID.randomUUID(), "User 2", "user2@example.com", "user2login", "password", UserType.CUSTOMER, null, null);
    }

    @Test
    void shouldReturnListOfUsers() {
        when(userGateway.findAll()).thenReturn(List.of(user1, user2));

        List<User> result = listUsersUseCase.execute();

        assertEquals(2, result.size(), "The size of the user list should be 2");
        assertEquals(user1, result.get(0), "The first user in the list should be user1");
        assertEquals(user2, result.get(1), "The second user in the list should be user2");
    }

    @Test
    void shouldReturnEmptyListWhenNoUsersFound() {
        when(userGateway.findAll()).thenReturn(List.of());
        List<User> result = listUsersUseCase.execute();
        assertEquals(0, result.size(), "The user list should be empty");
    }
}
