package app.jabafood.cleanarch.domain.useCases.restaurant;

import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.domain.entities.User;
import app.jabafood.cleanarch.domain.enums.UserType;
import app.jabafood.cleanarch.domain.gateways.IRestaurantGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

class ListRestaurantsUseCaseTest {

    private ListRestaurantsUseCase listRestaurantsUseCase;
    private IRestaurantGateway restaurantGateway;

    @BeforeEach
    void setup() {
        restaurantGateway = mock(IRestaurantGateway.class);
        listRestaurantsUseCase = new ListRestaurantsUseCase(restaurantGateway);
    }

    @Test
    void shouldReturnAllRestaurants() {
        // Given
        UUID restaurant1Id = UUID.randomUUID();
        UUID restaurant2Id = UUID.randomUUID();

        User owner1 = new User(UUID.randomUUID(), "John Doe 1", "johndoe", "john@example.com", "password", UserType.RESTAURANT_OWNER, null, null);
        User owner2 = new User(UUID.randomUUID(), "John Doe 2", "johndoe", "john@example.com", "password", UserType.RESTAURANT_OWNER, null, null);

        Restaurant restaurant1 = new Restaurant(restaurant1Id, "Pizza Express", null, null, null, null, owner1);
        Restaurant restaurant2 = new Restaurant(restaurant2Id, "Burger King", null, null, null, null, owner2);

        List<Restaurant> restaurants = List.of(restaurant1, restaurant2);

        when(restaurantGateway.findAll()).thenReturn(restaurants);

        // When
        List<Restaurant> result = listRestaurantsUseCase.execute();

        // Then
        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyInAnyOrder(restaurant1, restaurant2);

        verify(restaurantGateway, times(1)).findAll();
    }

    @Test
    void shouldReturnEmptyListIfNoRestaurantsExist() {
        // Given
        when(restaurantGateway.findAll()).thenReturn(Collections.emptyList());

        // When
        List<Restaurant> result = listRestaurantsUseCase.execute();

        // Then
        assertThat(result).isEmpty();

        verify(restaurantGateway, times(1)).findAll();
    }

    @Test
    void shouldHandleErrorsWhenFetchingRestaurants() {
        // Given
        when(restaurantGateway.findAll()).thenThrow(new RuntimeException("Database error"));

        // When / Then
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> listRestaurantsUseCase.execute())
                .withMessage("Database error");

        verify(restaurantGateway, times(1)).findAll();
    }

}
