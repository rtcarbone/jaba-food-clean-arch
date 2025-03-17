package app.jabafood.cleanarch.domain.useCases.user;

import app.jabafood.cleanarch.domain.entities.Address;
import app.jabafood.cleanarch.domain.entities.User;
import app.jabafood.cleanarch.domain.enums.UserType;
import app.jabafood.cleanarch.domain.exceptions.*;
import app.jabafood.cleanarch.domain.gateways.IUserGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseTest {

    private static final UUID USER_ID = UUID.randomUUID();
    private static final String NAME = "John Doe";
    private static final String EMAIL = "john.doe@example.com";
    private static final String LOGIN = "johndoe";
    private static final String PASSWORD = "123456";
    private static final UserType USER_TYPE = UserType.CUSTOMER;
    private static final LocalDateTime CREATED_AT = LocalDateTime.now();

    @Mock
    private IUserGateway userGateway;

    @Mock
    private Address address;

    @InjectMocks
    private CreateUserUseCase createUserUseCase;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(USER_ID, NAME, EMAIL, LOGIN, PASSWORD, USER_TYPE, CREATED_AT, address);
    }

    @Test
    void shouldCreateUserSuccessfully() {
        // Arrange
        when(userGateway.findByEmail(EMAIL)).thenReturn(Optional.empty());
        when(userGateway.findByLogin(LOGIN)).thenReturn(Optional.empty());
        when(userGateway.save(any(User.class))).thenReturn(user);

        // Act
        User createdUser = createUserUseCase.execute(user);

        // Assert
        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getEmail()).isEqualTo(EMAIL);

        verify(userGateway).findByEmail(EMAIL);
        verify(userGateway).findByLogin(LOGIN);
        verify(userGateway).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        // Arrange
        when(userGateway.findByEmail(EMAIL)).thenReturn(Optional.of(user));

        // Act & Assert
        assertThatThrownBy(() -> createUserUseCase.execute(user))
                .isInstanceOf(EmailAlreadyInUseException.class)
                .hasMessage("Email already in use");

        verify(userGateway).findByEmail(EMAIL);
        verify(userGateway, never()).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenLoginAlreadyExists() {
        // Arrange
        when(userGateway.findByEmail(EMAIL)).thenReturn(Optional.empty());
        when(userGateway.findByLogin(LOGIN)).thenReturn(Optional.of(user));

        // Act & Assert
        assertThatThrownBy(() -> createUserUseCase.execute(user))
                .isInstanceOf(LoginAlreadyInUseException.class)
                .hasMessage("Login already in use");

        verify(userGateway).findByEmail(EMAIL);
        verify(userGateway).findByLogin(LOGIN);
        verify(userGateway, never()).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenUserIdAlreadyExists() {
        // Arrange
        when(userGateway.findByEmail(EMAIL)).thenReturn(Optional.empty());
        when(userGateway.findByLogin(LOGIN)).thenReturn(Optional.empty());
        when(userGateway.findById(USER_ID)).thenReturn(Optional.of(user));

        // Act & Assert
        assertThatThrownBy(() -> createUserUseCase.execute(user))
                .isInstanceOf(UserAlreadyExistsException.class)
                .hasMessage("User already exists");

        verify(userGateway).findByEmail(EMAIL);
        verify(userGateway).findByLogin(LOGIN);
        verify(userGateway).findById(USER_ID);
        verify(userGateway, never()).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenUserValidationFails() {
        // Arrange
        User invalidUser = new User(null, "", "email@example.com", "login", "password", USER_TYPE, CREATED_AT, address);

        // Act & Assert
        assertThatThrownBy(() -> createUserUseCase.execute(invalidUser))
                .isInstanceOf(UserMandatoryFieldException.class)
                .hasMessage("The field 'name' is mandatory for user registration.");

        verify(userGateway, never()).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenEmailIsInvalid() {
        // Arrange
        user = new User(USER_ID, NAME, "invalid-email", LOGIN, PASSWORD, USER_TYPE, CREATED_AT, address);

        // Act & Assert
        assertThatThrownBy(() -> createUserUseCase.execute(user))
                .isInstanceOf(EmailFormatException.class);

        verify(userGateway, never()).save(any(User.class));
    }
}
