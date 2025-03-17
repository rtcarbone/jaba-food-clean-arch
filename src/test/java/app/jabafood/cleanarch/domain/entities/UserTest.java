package app.jabafood.cleanarch.domain.entities;

import app.jabafood.cleanarch.domain.enums.UserType;
import app.jabafood.cleanarch.domain.exceptions.UserMandatoryFieldException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserTest {

    private static final UUID USER_ID = UUID.randomUUID();
    private static final String NAME = "John Doe";
    private static final String EMAIL = "john.doe@example.com";
    private static final String LOGIN = "johndoe";
    private static final String PASSWORD = "123456";
    private static final UserType USER_TYPE = UserType.CUSTOMER;
    private static final LocalDateTime CREATED_AT = LocalDateTime.now();

    @Mock
    private Address address;

    @InjectMocks
    private User user;

    @BeforeEach
    void setUp() {
        user = new User(USER_ID, NAME, EMAIL, LOGIN, PASSWORD, USER_TYPE, CREATED_AT, address);
    }

    @Test
    void shouldCreateUserWithValidAttributes() {
        assertAll("Validating user attributes",
                  () -> assertThat(user.getId()).isEqualTo(USER_ID),
                  () -> assertThat(user.getName()).isEqualTo(NAME),
                  () -> assertThat(user.getEmail()).isEqualTo(EMAIL),
                  () -> assertThat(user.getLogin()).isEqualTo(LOGIN),
                  () -> assertThat(user.getPassword()).isEqualTo(PASSWORD),
                  () -> assertThat(user.getUserType()).isEqualTo(USER_TYPE),
                  () -> assertThat(user.getCreatedAt()).isEqualTo(CREATED_AT),
                  () -> assertThat(user.getAddress()).isNotNull()
        );
    }

    @Test
    void shouldGenerateValidUUID() {
        assertThat(user.getId()).isNotNull();
        assertThat(user.getId().toString()).matches("^[0-9a-fA-F-]{36}$");
    }

    @Test
    void shouldCreateUserWithNullEmail() {
        User userWithoutEmail = new User(USER_ID, NAME, null, LOGIN, PASSWORD, USER_TYPE, CREATED_AT, address);
        assertThat(userWithoutEmail.getEmail()).isNull();
    }

    @Test
    void shouldCopyUserWithUpdatedAttributes() {
        User copiedUser = user.copyWith("Jane Doe", "jane.doe@example.com", "janedoe", "654321", UserType.RESTAURANT_OWNER, address);

        assertAll("Validating copied user attributes",
                  () -> assertThat(copiedUser.getId()).isEqualTo(user.getId()),
                  () -> assertThat(copiedUser.getName()).isEqualTo("Jane Doe"),
                  () -> assertThat(copiedUser.getEmail()).isEqualTo("jane.doe@example.com"),
                  () -> assertThat(copiedUser.getLogin()).isEqualTo("janedoe"),
                  () -> assertThat(copiedUser.getPassword()).isEqualTo("654321"),
                  () -> assertThat(copiedUser.getUserType()).isEqualTo(UserType.RESTAURANT_OWNER),
                  () -> assertThat(copiedUser.getAddress()).isEqualTo(address)
        );
    }

    @Test
    void shouldCopyUserAndPreserveOriginalValuesWhenNullIsPassed() {
        User copiedUser = user.copyWith(null, null, null, null, null, null);

        assertAll("Ensuring unchanged values",
                  () -> assertThat(copiedUser.getId()).isEqualTo(user.getId()),
                  () -> assertThat(copiedUser.getName()).isEqualTo(user.getName()),
                  () -> assertThat(copiedUser.getEmail()).isEqualTo(user.getEmail()),
                  () -> assertThat(copiedUser.getLogin()).isEqualTo(user.getLogin()),
                  () -> assertThat(copiedUser.getPassword()).isEqualTo(user.getPassword()),
                  () -> assertThat(copiedUser.getUserType()).isEqualTo(user.getUserType()),
                  () -> assertThat(copiedUser.getAddress()).isEqualTo(user.getAddress())
        );
    }

    @Test
    void shouldUpdateOnlyOneAttributeWhenUsingCopyWith() {
        User copiedUser = user.copyWith("Updated Name", null, null, null, null, null);

        assertAll("Ensuring only the name was changed",
                  () -> assertThat(copiedUser.getName()).isEqualTo("Updated Name"),
                  () -> assertThat(copiedUser.getEmail()).isEqualTo(user.getEmail()),
                  () -> assertThat(copiedUser.getLogin()).isEqualTo(user.getLogin()),
                  () -> assertThat(copiedUser.getPassword()).isEqualTo(user.getPassword()),
                  () -> assertThat(copiedUser.getUserType()).isEqualTo(user.getUserType()),
                  () -> assertThat(copiedUser.getAddress()).isEqualTo(user.getAddress())
        );
    }

    @Test
    void shouldValidateSuccessfullyWhenAllFieldsArePresent() {
        assertThatCode(user::validate).doesNotThrowAnyException();
    }

    @Test
    void shouldThrowExceptionWhenNameIsNullOrEmpty() {
        assertAll("Validating name",
                  () -> assertThatThrownBy(() -> new User(USER_ID, null, EMAIL, LOGIN, PASSWORD, USER_TYPE, CREATED_AT, address).validate())
                          .isInstanceOf(UserMandatoryFieldException.class)
                          .hasMessage("The field 'name' is mandatory for user registration."),
                  () -> assertThatThrownBy(() -> new User(USER_ID, " ", EMAIL, LOGIN, PASSWORD, USER_TYPE, CREATED_AT, address).validate())
                          .isInstanceOf(UserMandatoryFieldException.class)
                          .hasMessage("The field 'name' is mandatory for user registration.")
        );
    }

    @Test
    void shouldThrowExceptionWhenAddressIsNull() {
        assertThatThrownBy(() -> new User(USER_ID, NAME, EMAIL, LOGIN, PASSWORD, USER_TYPE, CREATED_AT, null).validate())
                .isInstanceOf(UserMandatoryFieldException.class)
                .hasMessage("The field 'address' is mandatory for user registration.");
    }

    @Test
    void shouldThrowExceptionWhenLoginIsNull() {
        assertThatThrownBy(() -> new User(USER_ID, NAME, EMAIL, null, PASSWORD, USER_TYPE, CREATED_AT, address).validate())
                .isInstanceOf(UserMandatoryFieldException.class)
                .hasMessage("The field 'login' is mandatory for user registration.");
    }

    @Test
    void shouldThrowExceptionWhenPasswordIsNull() {
        assertThatThrownBy(() -> new User(USER_ID, NAME, EMAIL, LOGIN, null, USER_TYPE, CREATED_AT, address).validate())
                .isInstanceOf(UserMandatoryFieldException.class)
                .hasMessage("The field 'password' is mandatory for user registration.");
    }

    @Test
    void shouldThrowExceptionWhenUserTypeIsNull() {
        assertThatThrownBy(() -> new User(USER_ID, NAME, EMAIL, LOGIN, PASSWORD, null, CREATED_AT, address).validate())
                .isInstanceOf(UserMandatoryFieldException.class)
                .hasMessage("The field 'userType' is mandatory for user registration.");
    }

    @Test
    void shouldCallValidateOnAddressWhenValidatingUser() {
        user.validate();
        verify(address, times(1)).validate();
    }
}
