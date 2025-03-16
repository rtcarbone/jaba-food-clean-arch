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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UpdateUserPasswordUseCaseTest {

    @Mock
    private IUserGateway userGateway;

    @InjectMocks
    private UpdateUserPasswordUseCase updateUserPasswordUseCase;

    private UUID userId;
    private User existingUser;
    private UserPassword userPassword;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        userId = UUID.randomUUID();
        existingUser = new User(userId, "John Doe", "john.doe@example.com", "johndoe", "oldPassword", UserType.CUSTOMER, null, null);

        userPassword = new UserPassword("oldPassword", "newPassword", "newPassword");
    }

    @Test
    void shouldUpdateUserPasswordSuccess() {
        // Criação de um usuário existente com uma senha
        Address address = mock(Address.class);
        User existingUser = new User(UUID.randomUUID(), "John Doe", "john.doe@example.com", "johndoe", "oldPassword", UserType.CUSTOMER, LocalDateTime.now(), address);

        // Mock do método findById
        when(userGateway.findById(existingUser.getId())).thenReturn(Optional.of(existingUser));

        // Criando um novo objeto UserPassword
        UserPassword userPassword = new UserPassword("oldPassword", "newPassword", "newPassword");

        // Executando a atualização da senha
        User updatedUser = updateUserPasswordUseCase.execute(existingUser.getId(), userPassword);

        // Verificando se a senha foi atualizada
        assertNotNull(updatedUser);  // Verifique se o updatedUser não é nulo
        assertEquals("newPassword", updatedUser.getPassword());
    }


    @Test
    void shouldThrowInvalidPasswordExceptionWhenOldPasswordIsIncorrect() {
        // Configuração do comportamento do mock para retornar o usuário
        when(userGateway.findById(userId)).thenReturn(Optional.of(existingUser));

        // Alterando a senha antiga no objeto userPassword para algo incorreto
        UserPassword invalidPassword = new UserPassword("wrongOldPassword", "newPassword", "newPassword");


        InvalidPasswordException exception = assertThrows(InvalidPasswordException.class, () -> {
            updateUserPasswordUseCase.execute(userId, invalidPassword);
        });

        assertEquals("The old password is invalid", exception.getMessage());

        verify(userGateway, times(1)).findById(userId);
        verify(userGateway, times(0)).save(any(User.class));
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenUserDoesNotExist() {
        when(userGateway.findById(userId)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> {
            updateUserPasswordUseCase.execute(userId, userPassword);
        });

        verify(userGateway, times(1)).findById(userId);
        verify(userGateway, times(0)).save(any(User.class));
    }
}
