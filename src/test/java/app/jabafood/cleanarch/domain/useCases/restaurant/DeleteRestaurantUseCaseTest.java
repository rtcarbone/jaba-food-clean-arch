package app.jabafood.cleanarch.domain.useCases.restaurant;

import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.domain.entities.User;
import app.jabafood.cleanarch.domain.enums.UserType;
import app.jabafood.cleanarch.domain.exceptions.RestaurantNotFoundException;
import app.jabafood.cleanarch.domain.exceptions.UserNotFoundException;
import app.jabafood.cleanarch.domain.gateways.IRestaurantGateway;
import app.jabafood.cleanarch.domain.gateways.IUserGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

        User owner = new User(ownerId, "John Doe", "johndoe", "john@example.com", "password", UserType.RESTAURANT_OWNER, null, null);
        Restaurant restaurant = new Restaurant(restaurantId, "Pizza Express", null, null, null, null, owner);

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

    @Test
    void shouldFailIfOwnerDoesNotExist() {
        // Given
        UUID restaurantId = UUID.randomUUID();
        UUID ownerId = UUID.randomUUID();

        User owner = new User(ownerId, "John Doe", "johndoe", "john@example.com", "password", UserType.RESTAURANT_OWNER, null, null);
        Restaurant restaurant = new Restaurant(restaurantId, "Pizza Express", null, null, null, null, owner);

        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        when(userGateway.findById(ownerId)).thenReturn(Optional.empty());

        // When / Then
        assertThatExceptionOfType(UserNotFoundException.class)
                .isThrownBy(() -> deleteRestaurantUseCase.execute(restaurantId))
                .withMessage("User with ID '" + ownerId + "' not found.");

        verify(restaurantGateway, never()).delete(any(UUID.class));
    }
}
