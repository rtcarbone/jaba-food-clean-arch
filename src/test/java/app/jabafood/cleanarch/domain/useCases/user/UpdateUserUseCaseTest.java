package app.jabafood.cleanarch.domain.useCases.user;

import app.jabafood.cleanarch.domain.entities.Address;
import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.domain.entities.User;
import app.jabafood.cleanarch.domain.enums.CuisineType;
import app.jabafood.cleanarch.domain.enums.UserType;
import app.jabafood.cleanarch.domain.exceptions.EmailAlreadyInUseException;
import app.jabafood.cleanarch.domain.exceptions.UpdateUserException;
import app.jabafood.cleanarch.domain.exceptions.UserNotFoundException;
import app.jabafood.cleanarch.domain.gateways.IRestaurantGateway;
import app.jabafood.cleanarch.domain.gateways.IUserGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateUserUseCaseTest {

    @Mock
    private IUserGateway userGateway;

    @Mock
    private IRestaurantGateway restaurantGateway;

    @InjectMocks
    private UpdateUserUseCase updateUserUseCase;

    private User existingUser;
    private User updatedUserData;
    private UUID userId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        existingUser = new User(userId, "John Doe", "john@example.com", "johndoe", "password123", UserType.CUSTOMER, LocalDateTime.now(), mock(Address.class));
        updatedUserData = new User(null, "John Updated", "john.updated@example.com", "johndoe", "newpassword123", UserType.CUSTOMER, LocalDateTime.now(), mock(Address.class));
    }

    @Test
    void shouldUpdateUserSuccessfully() {
        UUID userId = UUID.randomUUID();
        String updatedName = "John Updated";
        String updatedEmail = "john.updated@example.com";
        String updatedLogin = "johnupdated";


        User existingUser = new User(userId, "John Doe", "john@example.com", "johndoe", "password", UserType.RESTAURANT_OWNER, LocalDateTime.now(), mock(Address.class));
        User updatedUser = new User(userId, updatedName, updatedEmail, updatedLogin, "newpassword", UserType.RESTAURANT_OWNER, LocalDateTime.now(), mock(Address.class));
        when(userGateway.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userGateway.findByEmail(updatedEmail)).thenReturn(Optional.empty());
        when(userGateway.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);

            assertEquals(updatedName, savedUser.getName());
            assertEquals(updatedEmail, savedUser.getEmail());
            assertEquals(updatedLogin, savedUser.getLogin());

            return savedUser;
        });


        User result = updateUserUseCase.execute(userId, updatedUser);
        assertEquals(updatedName, result.getName());
        assertEquals(updatedEmail, result.getEmail());
        assertEquals(updatedLogin, result.getLogin());


        verify(userGateway).save(any(User.class));
    }


    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        when(userGateway.findById(userId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> updateUserUseCase.execute(userId, updatedUserData))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("User with ID '" + userId + "' not found.");
    }

    @Test
    void shouldThrowExceptionWhenEmailIsAlreadyInUse() {
        when(userGateway.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userGateway.findByEmail(updatedUserData.getEmail())).thenReturn(Optional.of(existingUser));

        assertThatThrownBy(() -> updateUserUseCase.execute(userId, updatedUserData))
                .isInstanceOf(EmailAlreadyInUseException.class)
                .hasMessage("Email already in use");
    }

    @Test
    void shouldThrowExceptionWhenUserTypeCannotBeChanged() {

        UUID userId = UUID.randomUUID();
        User existingUser = new User(
                userId,
                "John Doe",
                "john@example.com",
                "johndoe",
                "password",
                UserType.RESTAURANT_OWNER,
                LocalDateTime.now(),
                mock(Address.class)
        );

        UUID id = UUID.randomUUID();
        String name = "Burger King";
        Address address = mock(Address.class);
        CuisineType cuisineType = CuisineType.BURGER;
        LocalTime openingTime = LocalTime.of(10, 0);
        LocalTime closingTime = LocalTime.of(22, 0);
        User owner = existingUser;

        List<Restaurant> associatedRestaurants = new ArrayList<>();
        associatedRestaurants.add(new Restaurant(UUID.randomUUID(), "Restaurant", address, cuisineType, openingTime, closingTime, owner));


        when(userGateway.findById(userId)).thenReturn(Optional.of(existingUser));
        when(restaurantGateway.findByOwnerId(userId)).thenReturn(associatedRestaurants);


        User updatedUser = new User(
                userId,
                "John Updated",
                "john.updated@example.com",
                "johnupdated",
                "newpassword",
                UserType.CUSTOMER, // Tentando alterar o tipo de usuário
                LocalDateTime.now(),
                mock(Address.class)
        );


        UpdateUserException exception = assertThrows(UpdateUserException.class, () -> {
            updateUserUseCase.execute(userId, updatedUser);
        });


        assertEquals("User type cannot be changed because the user is associated with one or more restaurants.", exception.getMessage());
    }

}
