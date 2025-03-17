package app.jabafood.cleanarch.domain.useCases.menuItem;

import app.jabafood.cleanarch.domain.entities.MenuItem;
import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.domain.exceptions.RestaurantNotFoundException;
import app.jabafood.cleanarch.domain.gateways.IMenuItemGateway;
import app.jabafood.cleanarch.domain.gateways.IRestaurantGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListMenuItemsByRestaurantUseCaseTest {

    private static final UUID RESTAURANT_ID = UUID.randomUUID();
    private static final UUID MENU_ITEM_1_ID = UUID.randomUUID();
    private static final UUID MENU_ITEM_2_ID = UUID.randomUUID();
    private static final String MENU_ITEM_1_NAME = "Pizza Margherita";
    private static final String MENU_ITEM_2_NAME = "Sushi Combo";
    private static final BigDecimal MENU_ITEM_1_PRICE = BigDecimal.valueOf(30.00);
    private static final BigDecimal MENU_ITEM_2_PRICE = BigDecimal.valueOf(50.00);
    private static final String MENU_ITEM_IMAGE = "/images/burger.png";

    @Mock
    private IMenuItemGateway menuItemGateway;

    @Mock
    private IRestaurantGateway restaurantGateway;

    @Mock
    private Restaurant restaurant;

    @InjectMocks
    private ListMenuItemsByRestaurantUseCase listMenuItemsByRestaurantUseCase;

    private MenuItem menuItem1;
    private MenuItem menuItem2;

    @BeforeEach
    void setup() {
        menuItem1 = new MenuItem(MENU_ITEM_1_ID, MENU_ITEM_1_NAME, "Deliciosa pizza tradicional", MENU_ITEM_1_PRICE, false, MENU_ITEM_IMAGE, restaurant);
        menuItem2 = new MenuItem(MENU_ITEM_2_ID, MENU_ITEM_2_NAME, "Variedade de sushis frescos", MENU_ITEM_2_PRICE, false, MENU_ITEM_IMAGE, restaurant);
    }

    @Test
    void shouldReturnMenuItemsByRestaurantSuccessfully() {
        // Arrange
        when(restaurantGateway.findById(RESTAURANT_ID)).thenReturn(Optional.of(restaurant));
        when(menuItemGateway.findByRestaurantId(RESTAURANT_ID)).thenReturn(List.of(menuItem1, menuItem2));

        // Act
        List<MenuItem> result = listMenuItemsByRestaurantUseCase.execute(RESTAURANT_ID);

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result.get(0)
                           .getName()).isEqualTo(MENU_ITEM_1_NAME);
        assertThat(result.get(1)
                           .getName()).isEqualTo(MENU_ITEM_2_NAME);

        verify(menuItemGateway, times(1)).findByRestaurantId(RESTAURANT_ID);
    }

    @Test
    void shouldReturnEmptyListWhenRestaurantHasNoMenuItems() {
        // Arrange
        when(restaurantGateway.findById(RESTAURANT_ID)).thenReturn(Optional.of(restaurant));
        when(menuItemGateway.findByRestaurantId(RESTAURANT_ID)).thenReturn(List.of());

        // Act
        List<MenuItem> result = listMenuItemsByRestaurantUseCase.execute(RESTAURANT_ID);

        // Assert
        assertThat(result).isEmpty();
        verify(menuItemGateway, times(1)).findByRestaurantId(RESTAURANT_ID);
    }

    @Test
    void shouldCallGatewayWithCorrectRestaurantId() {
        // Arrange
        when(restaurantGateway.findById(RESTAURANT_ID)).thenReturn(Optional.of(restaurant));
        when(menuItemGateway.findByRestaurantId(RESTAURANT_ID)).thenReturn(List.of(menuItem1));

        // Act
        listMenuItemsByRestaurantUseCase.execute(RESTAURANT_ID);

        // Assert
        verify(restaurantGateway, times(1)).findById(RESTAURANT_ID);
        verify(menuItemGateway, times(1)).findByRestaurantId(RESTAURANT_ID);
    }

    @Test
    void shouldThrowRestaurantNotFoundExceptionWhenRestaurantDoesNotExist() {
        // Arrange
        when(restaurantGateway.findById(RESTAURANT_ID)).thenReturn(Optional.empty());

        // Act / Assert
        RestaurantNotFoundException exception = assertThrows(RestaurantNotFoundException.class,
                                                             () -> listMenuItemsByRestaurantUseCase.execute(RESTAURANT_ID));

        assertThat(exception.getMessage()).isEqualTo("Restaurant with ID '" + RESTAURANT_ID + "' not found.");

        verify(menuItemGateway, never()).findByRestaurantId(any(UUID.class));
    }
}
