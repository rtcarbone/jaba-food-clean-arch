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
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateMenuItemUseCaseTest {

    private static final UUID RESTAURANT_ID = UUID.randomUUID();
    private static final UUID MENU_ITEM_ID = UUID.randomUUID();
    private static final String MENU_ITEM_NAME = "Burger";
    private static final String MENU_ITEM_DESC = "Delicious burger";
    private static final BigDecimal MENU_ITEM_PRICE = BigDecimal.valueOf(15.99);
    private static final String MENU_ITEM_IMAGE = "/images/burger.png";
    private static final boolean MENU_ITEM_VEGAN = false;

    @Mock
    private IMenuItemGateway menuItemGateway;

    @Mock
    private IRestaurantGateway restaurantGateway;

    @Mock
    private Restaurant restaurant;

    @InjectMocks
    private CreateMenuItemUseCase createMenuItemUseCase;

    private MenuItem menuItem;

    @BeforeEach
    void setup() {
        menuItem = new MenuItem(MENU_ITEM_ID, MENU_ITEM_NAME, MENU_ITEM_DESC, MENU_ITEM_PRICE, MENU_ITEM_VEGAN, MENU_ITEM_IMAGE, restaurant);
    }

    @Test
    void shouldCreateMenuItemSuccessfully() {
        // Arrange
        when(restaurant.getId()).thenReturn(RESTAURANT_ID);
        when(restaurantGateway.findById(RESTAURANT_ID)).thenReturn(Optional.of(restaurant));
        when(menuItemGateway.save(any(MenuItem.class))).thenReturn(menuItem);

        // Act
        MenuItem createdMenuItem = createMenuItemUseCase.execute(menuItem);

        // Assert
        assertThat(createdMenuItem).isNotNull();
        assertThat(createdMenuItem.getName()).isEqualTo(MENU_ITEM_NAME);
        assertThat(createdMenuItem.getPrice()).isEqualTo(MENU_ITEM_PRICE);
        assertThat(createdMenuItem.getRestaurant()).isEqualTo(restaurant);

        verify(menuItemGateway, times(1)).save(menuItem);
    }

    @Test
    void shouldFailIfRestaurantDoesNotExist() {
        // Arrange
        when(restaurant.getId()).thenReturn(RESTAURANT_ID);
        when(restaurantGateway.findById(RESTAURANT_ID)).thenReturn(Optional.empty());

        // Act & Assert
        RestaurantNotFoundException exception = assertThrows(
                RestaurantNotFoundException.class,
                () -> createMenuItemUseCase.execute(menuItem)
        );

        assertThat(exception.getMessage()).isEqualTo("Restaurant with ID '" + RESTAURANT_ID + "' not found.");
    }
}
