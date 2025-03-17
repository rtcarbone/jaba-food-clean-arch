package app.jabafood.cleanarch.domain.useCases.restaurant;

import app.jabafood.cleanarch.domain.entities.Address;
import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.domain.entities.User;
import app.jabafood.cleanarch.domain.enums.CuisineType;
import app.jabafood.cleanarch.domain.enums.UserType;
import app.jabafood.cleanarch.domain.exceptions.InvalidClosingTimeException;
import app.jabafood.cleanarch.domain.exceptions.RestaurantNotFoundException;
import app.jabafood.cleanarch.domain.gateways.IRestaurantGateway;
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
class UpdateRestaurantUseCaseTest {

    private static final UUID RESTAURANT_ID = UUID.randomUUID();
    private static final String OLD_NAME = "Old Name";
    private static final String NEW_NAME = "New Name";
    private static final CuisineType OLD_CUISINE = CuisineType.PIZZERIA;
    private static final CuisineType NEW_CUISINE = CuisineType.ITALIAN;
    private static final LocalTime OLD_OPENING_TIME = LocalTime.of(10, 0);
    private static final LocalTime OLD_CLOSING_TIME = LocalTime.of(22, 0);
    private static final LocalTime NEW_OPENING_TIME = LocalTime.of(9, 0);
    private static final LocalTime NEW_CLOSING_TIME = LocalTime.of(23, 0);
    private static final LocalTime INVALID_CLOSING_TIME = LocalTime.of(7, 0);

    @Mock
    private IRestaurantGateway restaurantGateway;

    @Mock
    private Address address;

    @InjectMocks
    private UpdateRestaurantUseCase updateRestaurantUseCase;

    private User owner;
    private Restaurant existingRestaurant;
    private Restaurant updatedData;

    @BeforeEach
    void setup() {
        owner = new User(UUID.randomUUID(), "John Doe", "john@example.com", "johndoe", "password", UserType.RESTAURANT_OWNER, null, null);
        existingRestaurant = new Restaurant(RESTAURANT_ID, OLD_NAME, address, OLD_CUISINE, OLD_OPENING_TIME, OLD_CLOSING_TIME, owner);
        updatedData = new Restaurant(RESTAURANT_ID, NEW_NAME, address, NEW_CUISINE, NEW_OPENING_TIME, NEW_CLOSING_TIME, owner);
    }

    @Test
    void shouldUpdateRestaurantSuccessfully() {
        // Arrange
        when(restaurantGateway.findById(RESTAURANT_ID)).thenReturn(Optional.of(existingRestaurant));
        when(restaurantGateway.save(any(Restaurant.class))).thenReturn(updatedData);

        // Act
        Restaurant updatedRestaurant = updateRestaurantUseCase.execute(RESTAURANT_ID, updatedData);

        // Assert
        assertThat(updatedRestaurant).isNotNull();
        assertThat(updatedRestaurant.getName()).isEqualTo(NEW_NAME);
        assertThat(updatedRestaurant.getCuisineType()).isEqualTo(NEW_CUISINE);
        assertThat(updatedRestaurant.getOpeningTime()).isEqualTo(NEW_OPENING_TIME);
        assertThat(updatedRestaurant.getClosingTime()).isEqualTo(NEW_CLOSING_TIME);

        verify(restaurantGateway, times(1)).findById(RESTAURANT_ID);
        verify(restaurantGateway, times(1)).save(any(Restaurant.class));
    }

    @Test
    void shouldFailIfRestaurantDoesNotExist() {
        // Arrange
        when(restaurantGateway.findById(RESTAURANT_ID)).thenReturn(Optional.empty());

        // Act & Assert
        RestaurantNotFoundException exception = assertThrows(RestaurantNotFoundException.class,
                                                             () -> updateRestaurantUseCase.execute(RESTAURANT_ID, updatedData));

        assertThat(exception.getMessage()).isEqualTo("Restaurant with ID '" + RESTAURANT_ID + "' not found.");

        verify(restaurantGateway, times(1)).findById(RESTAURANT_ID);
        verify(restaurantGateway, never()).save(any(Restaurant.class));
    }

    @Test
    void shouldFailIfClosingTimeIsBeforeOpeningTime() {
        // Arrange
        Restaurant invalidUpdatedData = new Restaurant(RESTAURANT_ID, NEW_NAME, address, OLD_CUISINE, NEW_OPENING_TIME, INVALID_CLOSING_TIME, owner);

        when(restaurantGateway.findById(RESTAURANT_ID)).thenReturn(Optional.of(existingRestaurant));

        // Act & Assert
        InvalidClosingTimeException exception = assertThrows(InvalidClosingTimeException.class,
                                                             () -> updateRestaurantUseCase.execute(RESTAURANT_ID, invalidUpdatedData));

        assertThat(exception.getMessage()).isEqualTo("Closing time must be later than opening time.");

        verify(restaurantGateway, times(1)).findById(RESTAURANT_ID);
        verify(restaurantGateway, never()).save(any(Restaurant.class));
    }
}
