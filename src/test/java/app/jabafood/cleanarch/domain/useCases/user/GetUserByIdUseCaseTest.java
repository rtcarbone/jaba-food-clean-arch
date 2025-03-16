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

public class GetUserByIdUseCaseTest {

    @Mock
    private IUserGateway userGateway;

    @InjectMocks
    private GetUserByIdUseCase getUserByIdUseCase;

    private UUID userId;
    private User existingUser;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        userId = UUID.randomUUID();
        existingUser = new User(userId, "John Doe", "john.doe@example.com", "johndoe", "password", UserType.CUSTOMER, null, null);
    }

    @Test
    void shouldReturnUserWhenFound() {

        when(userGateway.findById(userId)).thenReturn(Optional.of(existingUser));

        User result = getUserByIdUseCase.execute(userId);

        assertNotNull(result);
        assertEquals(existingUser, result);

        verify(userGateway, times(1)).findById(userId);
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenUserDoesNotExist() {
        // Configuração do comportamento do mock para não encontrar o usuário
        when(userGateway.findById(userId)).thenReturn(Optional.empty());

        // Executando o caso de uso e verificando se a exceção é lançada
         assertThrows(UserNotFoundException.class, () -> {
            getUserByIdUseCase.execute(userId);
        });


        // Verificando se o método findById foi chamado corretamente
        verify(userGateway, times(1)).findById(userId);
    }

}
