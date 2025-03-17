package app.jabafood.cleanarch.domain.useCases.menuItem;

import app.jabafood.cleanarch.domain.entities.MenuItem;
import app.jabafood.cleanarch.domain.entities.Restaurant;
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
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetMenuItemByIdUseCaseTest {

    private static final UUID MENU_ITEM_ID = UUID.randomUUID();
    private static final String MENU_ITEM_NAME = "Pizza Margherita";
    private static final String MENU_ITEM_DESC = "Tomato, mozzarella, and basil";
    private static final BigDecimal MENU_ITEM_PRICE = BigDecimal.valueOf(29.99);
    private static final String MENU_ITEM_IMAGE = "/images/burger.png";
    private static final boolean MENU_ITEM_VEGAN = false;

    @Mock
    private IMenuItemGateway menuItemGateway;

    @Mock
    private Restaurant restaurant;

    @InjectMocks
    private GetMenuItemByIdUseCase getMenuItemByIdUseCase;

    private MenuItem menuItem;

    @BeforeEach
    void setup() {
        menuItem = new MenuItem(MENU_ITEM_ID, MENU_ITEM_NAME, MENU_ITEM_DESC, MENU_ITEM_PRICE, MENU_ITEM_VEGAN, MENU_ITEM_IMAGE, restaurant);
    }

    @Test
    void shouldReturnMenuItemIfExists() {
        // Arrange
        when(menuItemGateway.findById(MENU_ITEM_ID)).thenReturn(Optional.of(menuItem));

        // Act
        MenuItem result = getMenuItemByIdUseCase.execute(MENU_ITEM_ID);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(MENU_ITEM_ID);
        assertThat(result.getName()).isEqualTo(MENU_ITEM_NAME);

        verify(menuItemGateway, times(1)).findById(MENU_ITEM_ID);
    }

    @Test
    void shouldThrowExceptionIfMenuItemNotFound() {
        // Arrange
        when(menuItemGateway.findById(MENU_ITEM_ID)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatExceptionOfType(MenuItemNotFoundException.class)
                .isThrownBy(() -> getMenuItemByIdUseCase.execute(MENU_ITEM_ID))
                .withMessage("Menu with ID '" + MENU_ITEM_ID + "' not found.");

        verify(menuItemGateway, times(1)).findById(MENU_ITEM_ID);
    }
}
