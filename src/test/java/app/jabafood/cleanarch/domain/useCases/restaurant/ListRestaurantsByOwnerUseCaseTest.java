package app.jabafood.cleanarch.domain.useCases.restaurant;

import app.jabafood.cleanarch.domain.entities.Address;
import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.domain.entities.User;
import app.jabafood.cleanarch.domain.enums.CuisineType;
import app.jabafood.cleanarch.domain.enums.UserType;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListRestaurantsByOwnerUseCaseTest {

    private static final UUID OWNER_ID = UUID.randomUUID();
    private static final String OWNER_NAME = "John Doe";
    private static final String OWNER_EMAIL = "john@example.com";
    private static final String OWNER_LOGIN = "johndoe";
    private static final String OWNER_PASSWORD = "password";

    private static final UUID RESTAURANT_1_ID = UUID.randomUUID();
    private static final UUID RESTAURANT_2_ID = UUID.randomUUID();
    private static final String RESTAURANT_1_NAME = "Pizza Express";
    private static final String RESTAURANT_2_NAME = "Sushi House";

    @Mock
    private IRestaurantGateway restaurantGateway;

    @Mock
    private IUserGateway userGateway;

    @Mock
    private Address address;

    @InjectMocks
    private ListRestaurantsByOwnerUseCase listRestaurantsByOwnerUseCase;

    private User owner;
    private Restaurant restaurant1;
    private Restaurant restaurant2;

    @BeforeEach
    void setup() {
        owner = new User(OWNER_ID, OWNER_NAME, OWNER_EMAIL, OWNER_LOGIN, OWNER_PASSWORD, UserType.RESTAURANT_OWNER, null, address);

        restaurant1 = new Restaurant(
                RESTAURANT_1_ID, RESTAURANT_1_NAME, address, CuisineType.PIZZERIA,
                LocalTime.of(10, 0), LocalTime.of(22, 0), owner
        );

        restaurant2 = new Restaurant(
                RESTAURANT_2_ID, RESTAURANT_2_NAME, address, CuisineType.JAPANESE,
                LocalTime.of(12, 0), LocalTime.of(23, 0), owner
        );
    }

    @Test
    void shouldReturnRestaurantsByOwnerSuccessfully() {
        // Arrange
        when(userGateway.findById(OWNER_ID)).thenReturn(Optional.of(owner));
        when(restaurantGateway.findByOwnerId(OWNER_ID)).thenReturn(List.of(restaurant1, restaurant2));

        // Act
        List<Restaurant> result = listRestaurantsByOwnerUseCase.execute(OWNER_ID);

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo(RESTAURANT_1_NAME);
        assertThat(result.get(1).getName()).isEqualTo(RESTAURANT_2_NAME);

        verify(userGateway, times(1)).findById(OWNER_ID);
        verify(restaurantGateway, times(1)).findByOwnerId(OWNER_ID);
    }

    @Test
    void shouldReturnEmptyListWhenOwnerHasNoRestaurants() {
        // Arrange
        when(userGateway.findById(OWNER_ID)).thenReturn(Optional.of(owner));
        when(restaurantGateway.findByOwnerId(OWNER_ID)).thenReturn(List.of());

        // Act
        List<Restaurant> result = listRestaurantsByOwnerUseCase.execute(OWNER_ID);

        // Assert
        assertThat(result).isEmpty();

        verify(userGateway, times(1)).findById(OWNER_ID);
        verify(restaurantGateway, times(1)).findByOwnerId(OWNER_ID);
    }

    @Test
    void shouldCallGatewayWithCorrectOwnerId() {
        // Arrange
        when(userGateway.findById(OWNER_ID)).thenReturn(Optional.of(owner));
        when(restaurantGateway.findByOwnerId(OWNER_ID)).thenReturn(List.of(restaurant1));

        // Act
        listRestaurantsByOwnerUseCase.execute(OWNER_ID);

        // Assert
        verify(userGateway, times(1)).findById(OWNER_ID);
        verify(restaurantGateway, times(1)).findByOwnerId(OWNER_ID);
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenOwnerDoesNotExist() {
        // Arrange
        when(userGateway.findById(OWNER_ID)).thenReturn(Optional.empty());

        // Act & Assert
        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                                                       () -> listRestaurantsByOwnerUseCase.execute(OWNER_ID));

        assertThat(exception.getMessage()).isEqualTo("User with ID '" + OWNER_ID + "' not found.");

        verify(userGateway, times(1)).findById(OWNER_ID);
        verify(restaurantGateway, never()).findByOwnerId(any(UUID.class));
    }
}
