package app.jabafood.cleanarch.domain.useCases.menuItem;

import app.jabafood.cleanarch.domain.entities.MenuItem;
import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.domain.gateways.IMenuItemGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListMenuItemsUseCaseTest {

    private static final UUID ITEM_1_ID = UUID.randomUUID();
    private static final UUID ITEM_2_ID = UUID.randomUUID();
    private static final String ITEM_1_NAME = "Sushi";
    private static final String ITEM_2_NAME = "Burger";
    private static final String ITEM_1_DESC = "Delicious sushi";
    private static final String ITEM_2_DESC = "Juicy burger";
    private static final BigDecimal ITEM_1_PRICE = BigDecimal.valueOf(10.99);
    private static final BigDecimal ITEM_2_PRICE = BigDecimal.valueOf(8.99);
    private static final String ITEM_IMAGE = "/images/burger.png";

    @Mock
    private IMenuItemGateway menuItemGateway;

    @Mock
    private Restaurant restaurant1;

    @Mock
    private Restaurant restaurant2;

    @InjectMocks
    private ListMenuItemUseCase listMenuItemsUseCase;

    private MenuItem item1;
    private MenuItem item2;

    @BeforeEach
    void setup() {
        item1 = new MenuItem(ITEM_1_ID, ITEM_1_NAME, ITEM_1_DESC, ITEM_1_PRICE, false, ITEM_IMAGE, restaurant1);
        item2 = new MenuItem(ITEM_2_ID, ITEM_2_NAME, ITEM_2_DESC, ITEM_2_PRICE, false, ITEM_IMAGE, restaurant2);
    }

    @Test
    void shouldReturnAllMenuItems() {
        // Arrange
        List<MenuItem> menuItems = List.of(item1, item2);
        when(menuItemGateway.findAll()).thenReturn(menuItems);

        // Act
        List<MenuItem> result = listMenuItemsUseCase.execute();

        // Assert
        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyInAnyOrder(item1, item2);

        verify(menuItemGateway, times(1)).findAll();
    }

    @Test
    void shouldReturnEmptyListIfNoMenuItemsExist() {
        // Arrange
        when(menuItemGateway.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<MenuItem> result = listMenuItemsUseCase.execute();

        // Assert
        assertThat(result).isEmpty();

        verify(menuItemGateway, times(1)).findAll();
    }

    @Test
    void shouldHandleErrorsWhenFetchingMenuItems() {
        // Arrange
        when(menuItemGateway.findAll()).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> listMenuItemsUseCase.execute())
                .withMessage("Database error");

        verify(menuItemGateway, times(1)).findAll();
    }
}
