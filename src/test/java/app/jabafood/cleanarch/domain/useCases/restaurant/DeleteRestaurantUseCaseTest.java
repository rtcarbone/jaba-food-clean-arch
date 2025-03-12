package app.jabafood.cleanarch.domain.useCases.restaurant;

import app.jabafood.cleanarch.domain.entities.Address;
import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.domain.entities.User;
import app.jabafood.cleanarch.domain.enums.CuisineType;
import app.jabafood.cleanarch.domain.enums.UserType;
import app.jabafood.cleanarch.domain.exceptions.RestaurantNotFoundException;
import app.jabafood.cleanarch.domain.gateways.IRestaurantGateway;
import app.jabafood.cleanarch.domain.gateways.IUserGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

class DeleteRestaurantUseCaseTest {

    private DeleteRestaurantUseCase deleteRestaurantUseCase;
    private IRestaurantGateway restaurantGateway;
    private IUserGateway userGateway;

    @BeforeEach
    void setup() {
        restaurantGateway = mock(IRestaurantGateway.class);
        userGateway = mock(IUserGateway.class);
        deleteRestaurantUseCase = new DeleteRestaurantUseCase(restaurantGateway);
    }

    @Test
    void shouldDeleteRestaurantSuccessfully() {
        // Given
        UUID ownerId = UUID.randomUUID();
        UUID restaurantId = UUID.randomUUID();

        User owner = new User(ownerId, "John Doe", "johndoe", "john@example.com", "password", UserType.RESTAURANT_OWNER, null, mock(Address.class));
        Restaurant restaurant = new Restaurant(restaurantId, "Pizza Express", mock(Address.class), CuisineType.JAPANESE, LocalTime.of(11, 0), LocalTime.of(23, 0), owner);

        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        when(userGateway.findById(ownerId)).thenReturn(Optional.of(owner));

        // When / Then
        assertDoesNotThrow(() -> deleteRestaurantUseCase.execute(restaurantId));

        verify(restaurantGateway, times(1)).delete(restaurantId);
    }

    @Test
    void shouldFailIfRestaurantDoesNotExist() {
        // Given
        UUID restaurantId = UUID.randomUUID();

        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.empty());

        // When / Then
        assertThatExceptionOfType(RestaurantNotFoundException.class)
                .isThrownBy(() -> deleteRestaurantUseCase.execute(restaurantId))
                .withMessage("Restaurant with ID '" + restaurantId + "' not found.");

        verify(restaurantGateway, never()).delete(any(UUID.class));
    }

}
