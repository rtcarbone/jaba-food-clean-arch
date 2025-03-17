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

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteMenuItemUseCaseTest {

    private static final UUID MENU_ITEM_ID = UUID.randomUUID();
    private static final String MENU_ITEM_NAME = "Margherita Pizza";
    private static final String MENU_ITEM_DESC = "Classic pizza with tomatoes and cheese";
    private static final BigDecimal MENU_ITEM_PRICE = BigDecimal.valueOf(12.99);
    private static final String MENU_ITEM_IMAGE = "/images/pizza.png";
    private static final boolean MENU_ITEM_VEGAN = false;

    @Mock
    private IMenuItemGateway menuItemGateway;

    @Mock
    private Restaurant restaurant;

    @InjectMocks
    private DeleteMenuItemUseCase deleteMenuItemUseCase;

    private MenuItem menuItem;

    @BeforeEach
    void setup() {
        menuItem = new MenuItem(MENU_ITEM_ID, MENU_ITEM_NAME, MENU_ITEM_DESC, MENU_ITEM_PRICE, MENU_ITEM_VEGAN, MENU_ITEM_IMAGE, restaurant);
    }

    @Test
    void shouldDeleteMenuItemSuccessfully() {
        // Arrange
        when(menuItemGateway.findById(MENU_ITEM_ID)).thenReturn(Optional.of(menuItem));

        // Act & Assert
        assertDoesNotThrow(() -> deleteMenuItemUseCase.execute(MENU_ITEM_ID));

        verify(menuItemGateway, times(1)).findById(MENU_ITEM_ID);
        verify(menuItemGateway, times(1)).delete(MENU_ITEM_ID);
    }

    @Test
    void shouldFailIfMenuItemDoesNotExist() {
        // Arrange
        when(menuItemGateway.findById(MENU_ITEM_ID)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatExceptionOfType(MenuItemNotFoundException.class)
                .isThrownBy(() -> deleteMenuItemUseCase.execute(MENU_ITEM_ID))
                .withMessage("Menu with ID '" + MENU_ITEM_ID + "' not found.");

        verify(menuItemGateway, times(1)).findById(MENU_ITEM_ID);
        verify(menuItemGateway, never()).delete(any(UUID.class));
    }
}
