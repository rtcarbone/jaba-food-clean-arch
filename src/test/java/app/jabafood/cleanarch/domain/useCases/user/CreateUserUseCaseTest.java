package app.jabafood.cleanarch.domain.useCases.user;

import app.jabafood.cleanarch.domain.entities.Address;
import app.jabafood.cleanarch.domain.entities.User;
import app.jabafood.cleanarch.domain.enums.UserType;
import app.jabafood.cleanarch.domain.exceptions.*;
import app.jabafood.cleanarch.domain.gateways.IUserGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;


import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

class CreateUserUseCaseTest {

    @Mock
    private IUserGateway userGateway;

    @InjectMocks
    private CreateUserUseCase createUserUseCase;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        UUID id = UUID.randomUUID();
        String name = "John Doe";
        String email = "john.doe@example.com";
        String login = "johndoe";
        String password = "123456";
        UserType userType = UserType.CUSTOMER;
        LocalDateTime createdAt = LocalDateTime.now();
        Address address = mock(Address.class);

        user = new User(id, name, email, login, password, userType, createdAt, address);
    }

    @Test
    void shouldCreateUserSuccessfully() {
        // Configura mocks
        when(userGateway.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(userGateway.findByLogin(user.getLogin())).thenReturn(Optional.empty());
        when(userGateway.save(any(User.class))).thenReturn(user);

        // Executa o caso de uso
        User createdUser = createUserUseCase.execute(user);

        // Validações
        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getEmail()).isEqualTo(user.getEmail());

        verify(userGateway).findByEmail(user.getEmail());
        verify(userGateway).findByLogin(user.getLogin());
        verify(userGateway).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        when(userGateway.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        assertThatThrownBy(() -> createUserUseCase.execute(user))
                .isInstanceOf(EmailAlreadyInUseException.class)
                .hasMessage("Email already in use");

        verify(userGateway).findByEmail(user.getEmail());
        verify(userGateway, never()).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenLoginAlreadyExists() {
        when(userGateway.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(userGateway.findByLogin(user.getLogin())).thenReturn(Optional.of(user));

        assertThatThrownBy(() -> createUserUseCase.execute(user))
                .isInstanceOf(LoginAlreadyInUseException.class)
                .hasMessage("Login already in use");

        verify(userGateway).findByLogin(user.getLogin());
        verify(userGateway, never()).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenUserIdAlreadyExists() {
        user = new User(UUID.randomUUID(), "John", "john.doe@example.com", "johndoe", "123456", UserType.CUSTOMER, LocalDateTime.now(), mock(Address.class));

        when(userGateway.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(userGateway.findByLogin(user.getLogin())).thenReturn(Optional.empty());
        when(userGateway.findById(user.getId())).thenReturn(Optional.of(user));

        assertThatThrownBy(() -> createUserUseCase.execute(user))
                .isInstanceOf(UserAlreadyExistsException.class)
                .hasMessage("User already exists");

        verify(userGateway).findById(user.getId());
        verify(userGateway, never()).save(any(User.class));
    }


    @Test
    void shouldThrowExceptionWhenUserValidationFails() {
        User invalidUser = new User(null, "", "email@example.com", "login", "password", UserType.CUSTOMER, LocalDateTime.now(), mock(Address.class));

        assertThatThrownBy(() -> createUserUseCase.execute(invalidUser))
                .isInstanceOf(UserMandatoryFieldException.class)
                .hasMessage("The field 'name' is mandatory for user registration.");

        verify(userGateway, never()).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenEmailIsInvalid() {
        user = new User(UUID.randomUUID(), "John", "invalid-email", "johndoe", "123456", UserType.CUSTOMER, LocalDateTime.now(), mock(Address.class));

        assertThatThrownBy(() -> createUserUseCase.execute(user))
                .isInstanceOf(EmailFormatException.class); // Se a validação lança outra exceção, troque aqui

        verify(userGateway, never()).save(any(User.class));
    }
}