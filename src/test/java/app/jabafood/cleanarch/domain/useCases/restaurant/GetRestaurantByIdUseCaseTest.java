package app.jabafood.cleanarch.domain.useCases.restaurant;

import app.jabafood.cleanarch.domain.entities.Address;
import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.domain.entities.User;
import app.jabafood.cleanarch.domain.enums.CuisineType;
import app.jabafood.cleanarch.domain.enums.UserType;
import app.jabafood.cleanarch.domain.exceptions.RestaurantNotFoundException;
import app.jabafood.cleanarch.domain.gateways.IRestaurantGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

class GetRestaurantByIdUseCaseTest {

    private GetRestaurantByIdUseCase getRestaurantByIdUseCase;
    private IRestaurantGateway restaurantGateway;

    @BeforeEach
    void setup() {
        restaurantGateway = mock(IRestaurantGateway.class);
        getRestaurantByIdUseCase = new GetRestaurantByIdUseCase(restaurantGateway);
    }

    @Test
    void shouldReturnRestaurantIfExists() {
        // Given
        User owner = new User(UUID.randomUUID(), "John Doe", "johndoe", "john@example.com", "password", UserType.RESTAURANT_OWNER, null, null);
        UUID restaurantId = UUID.randomUUID();
        Restaurant restaurant = new Restaurant(restaurantId, "Pizza Express", mock(Address.class), CuisineType.JAPANESE, LocalTime.of(11, 0), LocalTime.of(23, 0), owner);

        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.of(restaurant));

        // When
        Restaurant result = getRestaurantByIdUseCase.execute(restaurantId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(restaurantId);
        assertThat(result.getName()).isEqualTo("Pizza Express");

        verify(restaurantGateway, times(1)).findById(restaurantId);
    }

    @Test
    void shouldThrowExceptionIfRestaurantNotFound() {
        // Given
        UUID restaurantId = UUID.randomUUID();
        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.empty());

        // When / Then
        assertThatExceptionOfType(RestaurantNotFoundException.class)
                .isThrownBy(() -> getRestaurantByIdUseCase.execute(restaurantId))
                .withMessage("Restaurant with ID '" + restaurantId + "' not found.");

        verify(restaurantGateway, times(1)).findById(restaurantId);
    }
}
