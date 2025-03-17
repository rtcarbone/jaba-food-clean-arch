package app.jabafood.cleanarch.domain.useCases.restaurant;

import app.jabafood.cleanarch.domain.entities.Address;
import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.domain.entities.User;
import app.jabafood.cleanarch.domain.enums.CuisineType;
import app.jabafood.cleanarch.domain.gateways.IRestaurantGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListRestaurantsUseCaseTest {

    private static final UUID RESTAURANT_1_ID = UUID.randomUUID();
    private static final UUID RESTAURANT_2_ID = UUID.randomUUID();
    private static final String RESTAURANT_1_NAME = "Pizza Express";
    private static final String RESTAURANT_2_NAME = "Burger King";
    private static final CuisineType CUISINE_TYPE = CuisineType.JAPANESE;
    private static final LocalTime OPENING_TIME = LocalTime.of(11, 0);
    private static final LocalTime CLOSING_TIME = LocalTime.of(23, 0);

    @Mock
    private IRestaurantGateway restaurantGateway;

    @Mock
    private Address address;

    @Mock
    private User owner1;

    @Mock
    private User owner2;

    @InjectMocks
    private ListRestaurantsUseCase listRestaurantsUseCase;

    private Restaurant restaurant1;
    private Restaurant restaurant2;

    @BeforeEach
    void setup() {
        restaurant1 = new Restaurant(RESTAURANT_1_ID, RESTAURANT_1_NAME, address, CUISINE_TYPE, OPENING_TIME, CLOSING_TIME, owner1);
        restaurant2 = new Restaurant(RESTAURANT_2_ID, RESTAURANT_2_NAME, address, CUISINE_TYPE, OPENING_TIME, CLOSING_TIME, owner2);
    }

    @Test
    void shouldReturnAllRestaurants() {
        // Arrange
        List<Restaurant> restaurants = List.of(restaurant1, restaurant2);
        when(restaurantGateway.findAll()).thenReturn(restaurants);

        // Act
        List<Restaurant> result = listRestaurantsUseCase.execute();

        // Assert
        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyInAnyOrder(restaurant1, restaurant2);

        verify(restaurantGateway, times(1)).findAll();
    }

    @Test
    void shouldReturnEmptyListIfNoRestaurantsExist() {
        // Arrange
        when(restaurantGateway.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<Restaurant> result = listRestaurantsUseCase.execute();

        // Assert
        assertThat(result).isEmpty();

        verify(restaurantGateway, times(1)).findAll();
    }

    @Test
    void shouldHandleErrorsWhenFetchingRestaurants() {
        // Arrange
        when(restaurantGateway.findAll()).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> listRestaurantsUseCase.execute())
                .withMessage("Database error");

        verify(restaurantGateway, times(1)).findAll();
    }
}
