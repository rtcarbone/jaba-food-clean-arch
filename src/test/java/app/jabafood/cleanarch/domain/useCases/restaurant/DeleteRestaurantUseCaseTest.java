package app.jabafood.cleanarch.domain.useCases.restaurant;

import app.jabafood.cleanarch.domain.entities.Address;
import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.domain.entities.User;
import app.jabafood.cleanarch.domain.enums.CuisineType;
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

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteRestaurantUseCaseTest {

    private static final UUID RESTAURANT_ID = UUID.randomUUID();
    private static final String RESTAURANT_NAME = "Pizza Express";
    private static final CuisineType CUISINE_TYPE = CuisineType.JAPANESE;
    private static final LocalTime OPENING_TIME = LocalTime.of(11, 0);
    private static final LocalTime CLOSING_TIME = LocalTime.of(23, 0);

    @Mock
    private IRestaurantGateway restaurantGateway;

    @Mock
    private Address address;

    @Mock
    private User owner;

    @InjectMocks
    private DeleteRestaurantUseCase deleteRestaurantUseCase;

    private Restaurant restaurant;

    @BeforeEach
    void setup() {
        restaurant = new Restaurant(RESTAURANT_ID, RESTAURANT_NAME, address, CUISINE_TYPE, OPENING_TIME, CLOSING_TIME, owner);
    }

    @Test
    void shouldDeleteRestaurantSuccessfully() {
        // Arrange
        when(restaurantGateway.findById(RESTAURANT_ID)).thenReturn(Optional.of(restaurant));

        // Act & Assert
        assertDoesNotThrow(() -> deleteRestaurantUseCase.execute(RESTAURANT_ID));

        verify(restaurantGateway, times(1)).findById(RESTAURANT_ID);
        verify(restaurantGateway, times(1)).delete(RESTAURANT_ID);
    }

    @Test
    void shouldFailIfRestaurantDoesNotExist() {
        // Arrange
        when(restaurantGateway.findById(RESTAURANT_ID)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatExceptionOfType(RestaurantNotFoundException.class)
                .isThrownBy(() -> deleteRestaurantUseCase.execute(RESTAURANT_ID))
                .withMessage("Restaurant with ID '" + RESTAURANT_ID + "' not found.");

        verify(restaurantGateway, times(1)).findById(RESTAURANT_ID);
        verify(restaurantGateway, never()).delete(any(UUID.class));
    }
}
