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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateRestaurantUseCaseTest {

    private static final UUID OWNER_ID = UUID.randomUUID();
    private static final UUID RESTAURANT_ID = UUID.randomUUID();
    private static final String RESTAURANT_NAME = "Pizza Express";
    private static final CuisineType CUISINE_TYPE = CuisineType.PIZZERIA;
    private static final LocalTime OPENING_TIME = LocalTime.of(10, 0);
    private static final LocalTime CLOSING_TIME = LocalTime.of(23, 0);

    @Mock
    private IRestaurantGateway restaurantGateway;

    @Mock
    private IUserGateway userGateway;

    @Mock
    private Address address;

    @InjectMocks
    private CreateRestaurantUseCase createRestaurantUseCase;

    private User validOwner;
    private Restaurant restaurant;

    @BeforeEach
    void setup() {
        validOwner = new User(OWNER_ID, "John Doe", "john@example.com", "johndoe", "password", UserType.RESTAURANT_OWNER, null, null);
        restaurant = new Restaurant(RESTAURANT_ID, RESTAURANT_NAME, address, CUISINE_TYPE, OPENING_TIME, CLOSING_TIME, validOwner);
    }

    @Test
    void shouldCreateRestaurantSuccessfully() {
        // Arrange
        when(userGateway.findById(OWNER_ID)).thenReturn(Optional.of(validOwner));
        when(restaurantGateway.save(any(Restaurant.class))).thenReturn(restaurant);

        // Act
        Restaurant createdRestaurant = createRestaurantUseCase.execute(restaurant);

        // Assert
        assertThat(createdRestaurant).isNotNull();
        assertThat(createdRestaurant.getName()).isEqualTo(RESTAURANT_NAME);
        assertThat(createdRestaurant.getCuisineType()).isEqualTo(CUISINE_TYPE);
        assertThat(createdRestaurant.getOwner()).isEqualTo(validOwner);

        verify(userGateway, times(1)).findById(OWNER_ID);
        verify(restaurantGateway, times(1)).save(any(Restaurant.class));
    }

    @Test
    void shouldFailIfOwnerDoesNotExist() {
        // Arrange
        when(userGateway.findById(OWNER_ID)).thenReturn(Optional.empty());

        // Act & Assert
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> createRestaurantUseCase.execute(restaurant));

        assertThat(exception.getMessage()).isEqualTo("User with ID '" + OWNER_ID + "' not found.");

        verify(userGateway, times(1)).findById(OWNER_ID);
        verify(restaurantGateway, never()).save(any(Restaurant.class));
    }

    @Test
    void shouldFailIfOwnerIsNotRestaurantOwner() {
        // Arrange
        User invalidOwner = new User(OWNER_ID, "Jane Doe", "jane@example.com", "janedoe", "password", UserType.CUSTOMER, null, null);
        when(userGateway.findById(OWNER_ID)).thenReturn(Optional.of(invalidOwner));

        // Act & Assert
        RestaurantOwnerInvalidException exception = assertThrows(RestaurantOwnerInvalidException.class, () -> createRestaurantUseCase.execute(restaurant));

        assertThat(exception.getMessage()).isEqualTo("User with ID '" + OWNER_ID + "' is not a valid owner for this restaurant.");

        verify(userGateway, times(1)).findById(OWNER_ID);
        verify(restaurantGateway, never()).save(any(Restaurant.class));
    }
}
