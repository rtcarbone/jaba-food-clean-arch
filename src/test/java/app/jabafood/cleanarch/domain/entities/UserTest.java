package app.jabafood.cleanarch.domain.entities;

import app.jabafood.cleanarch.domain.enums.UserType;
import app.jabafood.cleanarch.domain.exceptions.UserMandatoryFieldException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserTest {

    private UUID id;
    private String name;
    private String email;
    private String login;
    private String password;
    private UserType userType;
    private LocalDateTime createdAt;
    private Address address;
    private User user;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();
        name = "John Doe";
        email = "john.doe@example.com";
        login = "johndoe";
        password = "123456";
        userType = UserType.CUSTOMER;
        createdAt = LocalDateTime.now();
        address = mock(Address.class);

        user = new User(id, name, email, login, password, userType, createdAt, address);
    }


    @Test
    void shouldCreateUserWithValidAttributes() {
        assertThat(user.getId()).isEqualTo(id);
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getLogin()).isEqualTo(login);
        assertThat(user.getPassword()).isEqualTo(password);
        assertThat(user.getUserType()).isEqualTo(userType);
        assertThat(user.getCreatedAt()).isEqualTo(createdAt);
        assertThat(user.getAddress()).isNotNull();
    }

    @Test
    void shouldGenerateValidUUID() {
        assertThat(user.getId()).isNotNull();
        assertThat(user.getId().toString()).matches("^[0-9a-fA-F-]{36}$");
    }

    @Test
    void shouldCreateUserWithNullEmail() {
        User userWithoutEmail = new User(id, name, null, login, password, userType, createdAt, address);
        assertThat(userWithoutEmail.getEmail()).isNull();
    }

    @Test
    void shouldCopyUserWithUpdatedAttributes() {
        String newName = "Jane Doe";
        String newEmail = "jane.doe@example.com";
        String newLogin = "janedoe";
        String newPassword = "654321";
        UserType newUserType = UserType.CUSTOMER;
        Address newAddress = mock(Address.class);

        User copiedUser = user.copyWith(newName, newEmail, newLogin, newPassword, newUserType, newAddress);

        assertThat(copiedUser.getId()).isEqualTo(user.getId());
        assertThat(copiedUser.getName()).isEqualTo(newName);
        assertThat(copiedUser.getEmail()).isEqualTo(newEmail);
        assertThat(copiedUser.getLogin()).isEqualTo(newLogin);
        assertThat(copiedUser.getPassword()).isEqualTo(newPassword);
        assertThat(copiedUser.getUserType()).isEqualTo(newUserType);
        assertThat(copiedUser.getAddress()).isEqualTo(newAddress);
    }

    @Test
    void shouldCopyUserAndPreserveOriginalValuesWhenNullIsPassed() {
        User copiedUser = user.copyWith(null, null, null, null, null, null);

        assertThat(copiedUser.getId()).isEqualTo(user.getId());
        assertThat(copiedUser.getName()).isEqualTo(user.getName());
        assertThat(copiedUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(copiedUser.getLogin()).isEqualTo(user.getLogin());
        assertThat(copiedUser.getPassword()).isEqualTo(user.getPassword());
        assertThat(copiedUser.getUserType()).isEqualTo(user.getUserType());
        assertThat(copiedUser.getAddress()).isEqualTo(user.getAddress());
    }

    @Test
    void shouldUpdateOnlyOneAttributeWhenUsingCopyWith() {
        String newName = "Updated Name";
        User copiedUser = user.copyWith(newName, null, null, null, null, null);

        assertThat(copiedUser.getName()).isEqualTo(newName);
        assertThat(copiedUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(copiedUser.getLogin()).isEqualTo(user.getLogin());
        assertThat(copiedUser.getPassword()).isEqualTo(user.getPassword());
        assertThat(copiedUser.getUserType()).isEqualTo(user.getUserType());
        assertThat(copiedUser.getAddress()).isEqualTo(user.getAddress());
    }


    @Test
    void shouldValidateSuccessfullyWhenAllFieldsArePresent() {
        assertThatCode(user::validate).doesNotThrowAnyException();
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        user = new User(id, null, email, login, password, userType, createdAt, address);
        assertThatThrownBy(user::validate)
                .isInstanceOf(UserMandatoryFieldException.class)
                .hasMessage("The field 'name' is mandatory for user registration.");
    }

    @Test
    void shouldThrowExceptionWhenNameIsEmpty() {
        user = new User(id, "  ", email, login, password, userType, createdAt, address);
        assertThatThrownBy(user::validate)
                .isInstanceOf(UserMandatoryFieldException.class)
                .hasMessage("The field 'name' is mandatory for user registration.");
    }

    @Test
    void shouldThrowExceptionWhenAddressIsNull() {
        user = new User(id, name, email, login, password, userType, createdAt, null);
        assertThatThrownBy(user::validate)
                .isInstanceOf(UserMandatoryFieldException.class)
                .hasMessage("The field 'address' is mandatory for user registration.");
    }

    @Test
    void shouldThrowExceptionWhenLoginIsNull() {
        user = new User(id, name, email, null, password, userType, createdAt, address);
        assertThatThrownBy(user::validate)
                .isInstanceOf(UserMandatoryFieldException.class)
                .hasMessage("The field 'login' is mandatory for user registration.");
    }

    @Test
    void shouldThrowExceptionWhenEmailIsNull() {
        user = new User(id, name, null, login, password, userType, createdAt, address);
        assertThatThrownBy(user::validate)
                .isInstanceOf(UserMandatoryFieldException.class)
                .hasMessage("The field 'email' is mandatory for user registration.");
    }

    @Test
    void shouldThrowExceptionWhenPasswordIsNull() {
        user = new User(id, name, email, login, null, userType, createdAt, address);
        assertThatThrownBy(user::validate)
                .isInstanceOf(UserMandatoryFieldException.class)
                .hasMessage("The field 'password' is mandatory for user registration.");
    }

    @Test
    void shouldThrowExceptionWhenUserTypeIsNull() {
        user = new User(id, name, email, login, password, null, createdAt, address);
        assertThatThrownBy(user::validate)
                .isInstanceOf(UserMandatoryFieldException.class)
                .hasMessage("The field 'userType' is mandatory for user registration.");
    }

    @Test
    void shouldCallValidateOnAddressWhenValidatingUser() {
        user.validate();
        verify(address, times(1)).validate();
    }
}
