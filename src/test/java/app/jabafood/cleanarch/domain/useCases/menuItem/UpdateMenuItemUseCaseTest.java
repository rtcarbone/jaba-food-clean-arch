package app.jabafood.cleanarch.domain.useCases.menuItem;

import app.jabafood.cleanarch.domain.entities.MenuItem;
import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.domain.exceptions.InvalidPriceException;
import app.jabafood.cleanarch.domain.exceptions.MenuItemNotFoundException;
import app.jabafood.cleanarch.domain.gateways.IMenuItemGateway;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateMenuItemUseCaseTest {

    private static final UUID MENU_ITEM_ID = UUID.randomUUID();
    private static final String OLD_ITEM_NAME = "Old Sushi";
    private static final String NEW_ITEM_NAME = "New Sushi";
    private static final String OLD_ITEM_DESC = "Delicious old sushi";
    private static final String NEW_ITEM_DESC = "Delicious new sushi";
    private static final BigDecimal OLD_ITEM_PRICE = BigDecimal.valueOf(10.99);
    private static final BigDecimal NEW_ITEM_PRICE = BigDecimal.valueOf(12.99);
    private static final BigDecimal INVALID_PRICE = BigDecimal.valueOf(-5.00);
    private static final String ITEM_IMAGE = "/images/burger.png";

    @Mock
    private IMenuItemGateway menuItemGateway;

    @InjectMocks
    private UpdateMenuItemUseCase updateMenuItemUseCase;

    private Restaurant restaurant;
    private MenuItem existingItem;
    private MenuItem updatedData;

    @BeforeEach
    void setup() {
        restaurant = new Restaurant(UUID.randomUUID(), "Pizza Place", null, null, null, null, null);
        existingItem = new MenuItem(MENU_ITEM_ID, OLD_ITEM_NAME, OLD_ITEM_DESC, OLD_ITEM_PRICE, false, ITEM_IMAGE, restaurant);
        updatedData = new MenuItem(MENU_ITEM_ID, NEW_ITEM_NAME, NEW_ITEM_DESC, NEW_ITEM_PRICE, false, ITEM_IMAGE, restaurant);
    }

    @Test
    void shouldUpdateMenuItemSuccessfully() {
        // Arrange
        when(menuItemGateway.findById(MENU_ITEM_ID)).thenReturn(Optional.of(existingItem));
        when(menuItemGateway.save(any(MenuItem.class))).thenReturn(updatedData);

        // Act
        MenuItem updatedMenuItem = updateMenuItemUseCase.execute(MENU_ITEM_ID, updatedData);

        // Assert
        assertThat(updatedMenuItem).isNotNull();
        assertThat(updatedMenuItem.getName()).isEqualTo(NEW_ITEM_NAME);
        assertThat(updatedMenuItem.getDescription()).isEqualTo(NEW_ITEM_DESC);
        assertThat(updatedMenuItem.getPrice()).isEqualTo(NEW_ITEM_PRICE);

        verify(menuItemGateway, times(1)).save(any(MenuItem.class));
    }

    @Test
    void shouldFailIfMenuItemDoesNotExist() {
        // Arrange
        when(menuItemGateway.findById(MENU_ITEM_ID)).thenReturn(Optional.empty());

        // Act & Assert
        MenuItemNotFoundException exception = assertThrows(MenuItemNotFoundException.class,
                                                           () -> updateMenuItemUseCase.execute(MENU_ITEM_ID, updatedData));

        assertThat(exception.getMessage()).isEqualTo("Menu with ID '" + MENU_ITEM_ID + "' not found.");
    }

    @Test
    void shouldHandleInvalidPrice() {
        // Arrange
        MenuItem invalidPriceData = new MenuItem(MENU_ITEM_ID, NEW_ITEM_NAME, NEW_ITEM_DESC, INVALID_PRICE, false, ITEM_IMAGE, restaurant);
        when(menuItemGateway.findById(MENU_ITEM_ID)).thenReturn(Optional.of(existingItem));

        // Act & Assert
        InvalidPriceException exception = assertThrows(InvalidPriceException.class,
                                                       () -> updateMenuItemUseCase.execute(MENU_ITEM_ID, invalidPriceData));

        assertThat(exception.getMessage()).isEqualTo("Price must be a value greater than or equal to 0.");
    }
}
