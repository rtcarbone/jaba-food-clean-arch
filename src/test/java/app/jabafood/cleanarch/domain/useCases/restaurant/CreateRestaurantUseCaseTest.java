package app.jabafood.cleanarch.domain.useCases.restaurant;

import app.jabafood.cleanarch.domain.entities.Address;
import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.domain.entities.User;
import app.jabafood.cleanarch.domain.enums.CuisineType;
import app.jabafood.cleanarch.domain.enums.UserType;
import app.jabafood.cleanarch.domain.exceptions.RestaurantOwnerInvalidException;
import app.jabafood.cleanarch.domain.exceptions.UserNotFoundException;
import app.jabafood.cleanarch.domain.gateways.IRestaurantGateway;
import app.jabafood.cleanarch.domain.gateways.IUserGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateRestaurantUseCaseTest {

    private CreateRestaurantUseCase createRestaurantUseCase;
    private IRestaurantGateway restaurantGateway;
    private IUserGateway userGateway;

    @BeforeEach
    void setup() {
        restaurantGateway = mock(IRestaurantGateway.class);
        userGateway = mock(IUserGateway.class);
        createRestaurantUseCase = new CreateRestaurantUseCase(restaurantGateway, userGateway);
    }

    @Test
    void shouldCreateRestaurantSuccessfully() {
        // Given
        UUID ownerId = UUID.randomUUID();
        User owner = new User(ownerId, "John Doe", "johndoe", "john@example.com", "password", UserType.RESTAURANT_OWNER, null, null);

        UUID restaurantId = UUID.randomUUID();
        Restaurant restaurant = new Restaurant(
                restaurantId, "Pizza Express", mock(Address.class), CuisineType.PIZZERIA,
                LocalTime.of(10, 0), LocalTime.of(23, 0), owner
        );

        when(userGateway.findById(ownerId)).thenReturn(Optional.of(owner));
        when(restaurantGateway.save(any(Restaurant.class))).thenReturn(restaurant);

        // When
        Restaurant createdRestaurant = createRestaurantUseCase.execute(restaurant);

        // Then
        assertThat(createdRestaurant).isNotNull();
        assertThat(createdRestaurant.getName()).isEqualTo("Pizza Express");
        assertThat(createdRestaurant.getCuisineType()).isEqualTo(CuisineType.PIZZERIA);
        assertThat(createdRestaurant.getOwner()).isEqualTo(owner);

        verify(restaurantGateway, times(1)).save(any(Restaurant.class));
    }

    @Test
    void shouldFailIfOwnerDoesNotExist() {
        // Given
        UUID ownerId = UUID.randomUUID();
        User owner = new User(ownerId, "John Doe", "johndoe", "john@example.com", "password", UserType.RESTAURANT_OWNER, null, null);

        Restaurant restaurant = new Restaurant(UUID.randomUUID(), "Sushi Place", mock(Address.class), CuisineType.JAPANESE, LocalTime.of(12, 0), LocalTime.of(22, 0), owner);

        when(userGateway.findById(ownerId)).thenReturn(Optional.empty());

        // When / Then
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> createRestaurantUseCase.execute(restaurant));
        assertThat(exception.getMessage()).isEqualTo("User with ID '" + ownerId + "' not found.");
    }

    @Test
    void shouldFailIfOwnerIsNotRestaurantOwner() {
        // Given
        UUID ownerId = UUID.randomUUID();
        User owner = new User(ownerId, "Jane Doe", "janedoe", "jane@example.com", "password", UserType.CUSTOMER, null, null);

        Restaurant restaurant = new Restaurant(UUID.randomUUID(), "Burger House", mock(Address.class), CuisineType.BURGER, LocalTime.of(11, 0), LocalTime.of(23, 0), owner);

        when(userGateway.findById(ownerId)).thenReturn(Optional.of(owner));

        // When / Then
        RestaurantOwnerInvalidException exception = assertThrows(RestaurantOwnerInvalidException.class, () -> createRestaurantUseCase.execute(restaurant));
        assertThat(exception.getMessage()).isEqualTo("User with ID '" + ownerId + "' is not a valid owner for this restaurant.");
    }

}
