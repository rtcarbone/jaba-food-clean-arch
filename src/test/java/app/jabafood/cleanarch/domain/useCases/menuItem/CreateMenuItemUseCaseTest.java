package app.jabafood.cleanarch.domain.useCases.menuItem;

import app.jabafood.cleanarch.domain.entities.Address;
import app.jabafood.cleanarch.domain.entities.MenuItem;
import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.domain.enums.CuisineType;
import app.jabafood.cleanarch.domain.exceptions.RestaurantNotFoundException;
import app.jabafood.cleanarch.domain.gateways.IMenuItemGateway;
import app.jabafood.cleanarch.domain.gateways.IRestaurantGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateMenuItemUseCaseTest {

    private CreateMenuItemUseCase createMenuItemUseCase;
    private IMenuItemGateway menuItemGateway;
    private IRestaurantGateway restaurantGateway;

    @BeforeEach
    void setup() {
        menuItemGateway = mock(IMenuItemGateway.class);
        restaurantGateway = mock(IRestaurantGateway.class);
        createMenuItemUseCase = new CreateMenuItemUseCase(menuItemGateway, restaurantGateway);
    }

    @Test
    void shouldCreateMenuItemSuccessfully() {
        // Given
        UUID restaurantId = UUID.randomUUID();
        Restaurant restaurant = new Restaurant(
                restaurantId, "Pizza Express", mock(Address.class), CuisineType.PIZZERIA,
                LocalTime.of(10, 0), LocalTime.of(23, 0), null);

        UUID menuItemId = UUID.randomUUID();
        MenuItem menuItem = new MenuItem(menuItemId, "Burger", "Delicious burger", BigDecimal.valueOf(15.99), false, "/images/burger.png", restaurant);

        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        when(menuItemGateway.save(any(MenuItem.class))).thenReturn(menuItem);

        // When
        MenuItem createdMenuItem = createMenuItemUseCase.execute(menuItem);

        // Then
        assertThat(createdMenuItem).isNotNull();
        assertThat(createdMenuItem.getName()).isEqualTo("Burger");
        assertThat(createdMenuItem.getPrice()).isEqualTo(BigDecimal.valueOf(15.99));
        assertThat(createdMenuItem.getRestaurant()).isEqualTo(restaurant);

        verify(menuItemGateway, times(1)).save(menuItem);
    }

    @Test
    void shouldFailIfRestaurantDoesNotExist() {
        // Given
        UUID restaurantId = UUID.randomUUID();
        Restaurant restaurant = new Restaurant(
                restaurantId, "Pizza Place", mock(Address.class), CuisineType.PIZZERIA,
                LocalTime.of(10, 0), LocalTime.of(23, 0), null);

        MenuItem menuItem = new MenuItem(UUID.randomUUID(), "Pizza", "Cheese pizza", BigDecimal.valueOf(20.00), true, "/images/pizza.png", restaurant);

        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.empty());

        // When / Then
        RestaurantNotFoundException exception = assertThrows(
                RestaurantNotFoundException.class,
                () -> createMenuItemUseCase.execute(menuItem)
        );

        assertThat(exception.getMessage()).isEqualTo("Restaurant with ID " + restaurantId + " not found.");
    }
}
