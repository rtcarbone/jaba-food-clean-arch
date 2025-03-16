package app.jabafood.cleanarch.domain.useCases.menuItem;

import app.jabafood.cleanarch.domain.entities.MenuItem;
import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.domain.exceptions.RestaurantNotFoundException;
import app.jabafood.cleanarch.domain.gateways.IMenuItemGateway;
import app.jabafood.cleanarch.domain.gateways.IRestaurantGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ListMenuItemsByRestaurantUseCaseTest {

    private ListMenuItemsByRestaurantUseCase listMenuItemsByRestaurantUseCase;
    private IMenuItemGateway menuItemGateway;
    private IRestaurantGateway restaurantGateway;

    private UUID restaurantId;
    private Restaurant restaurant;
    private MenuItem menuItem1;
    private MenuItem menuItem2;

    @BeforeEach
    void setup() {
        menuItemGateway = mock(IMenuItemGateway.class);
        restaurantGateway = mock(IRestaurantGateway.class);
        listMenuItemsByRestaurantUseCase = new ListMenuItemsByRestaurantUseCase(menuItemGateway, restaurantGateway);

        restaurantId = UUID.randomUUID();
        restaurant = mock(Restaurant.class);

        menuItem1 = new MenuItem(UUID.randomUUID(), "Pizza Margherita", "Deliciosa pizza tradicional", BigDecimal.valueOf(30.00), false, "/images/burger.png", restaurant);
        menuItem2 = new MenuItem(UUID.randomUUID(), "Sushi Combo", "Variedade de sushis frescos", BigDecimal.valueOf(50.00),false, "/images/burger.png", restaurant);
    }

    @Test
    void shouldReturnMenuItemsByRestaurantSuccessfully() {
        // Arrange
        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        when(menuItemGateway.findByRestaurantId(restaurantId)).thenReturn(List.of(menuItem1, menuItem2));

        // Act
        List<MenuItem> result = listMenuItemsByRestaurantUseCase.execute(restaurantId);

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("Pizza Margherita");
        assertThat(result.get(1).getName()).isEqualTo("Sushi Combo");

        verify(menuItemGateway, times(1)).findByRestaurantId(restaurantId);
    }

    @Test
    void shouldReturnEmptyListWhenRestaurantHasNoMenuItems() {
        // Arrange
        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        when(menuItemGateway.findByRestaurantId(restaurantId)).thenReturn(List.of());

        // Act
        List<MenuItem> result = listMenuItemsByRestaurantUseCase.execute(restaurantId);

        // Assert
        assertThat(result).isEmpty();
        verify(menuItemGateway, times(1)).findByRestaurantId(restaurantId);
    }

    @Test
    void shouldCallGatewayWithCorrectRestaurantId() {
        // Arrange
        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        when(menuItemGateway.findByRestaurantId(restaurantId)).thenReturn(List.of(menuItem1));

        // Act
        listMenuItemsByRestaurantUseCase.execute(restaurantId);

        // Assert
        verify(restaurantGateway, times(1)).findById(restaurantId);
        verify(menuItemGateway, times(1)).findByRestaurantId(restaurantId);
    }

    @Test
    void shouldThrowRestaurantNotFoundExceptionWhenRestaurantDoesNotExist() {
        // Arrange
        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.empty());

        // Act / Assert
        RestaurantNotFoundException exception = assertThrows(RestaurantNotFoundException.class,
                () -> listMenuItemsByRestaurantUseCase.execute(restaurantId));

        assertThat(exception.getMessage()).isEqualTo("Restaurant with ID '" + restaurantId + "' not found.");

        verify(menuItemGateway, never()).findByRestaurantId(any(UUID.class));
    }
}
