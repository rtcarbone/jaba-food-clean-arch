package app.jabafood.cleanarch.domain.useCases.menuItem;

import app.jabafood.cleanarch.domain.entities.Address;
import app.jabafood.cleanarch.domain.entities.MenuItem;
import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.domain.enums.CuisineType;
import app.jabafood.cleanarch.domain.gateways.IMenuItemGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

class ListMenuItemsUseCaseTest {

    private ListMenuItemUseCase listMenuItemsUseCase;
    private IMenuItemGateway menuItemGateway;

    @BeforeEach
    void setup() {
        menuItemGateway = mock(IMenuItemGateway.class);
        listMenuItemsUseCase = new ListMenuItemUseCase(menuItemGateway);
    }

    @Test
    void shouldReturnAllMenuItems() {
        // Given
        UUID item1Id = UUID.randomUUID();
        UUID item2Id = UUID.randomUUID();

        Restaurant restaurant1 = new Restaurant(UUID.randomUUID(), "Pizza Express", mock(Address.class), CuisineType.JAPANESE, LocalTime.of(11, 0), LocalTime.of(23, 0), null);
        Restaurant restaurant2 = new Restaurant(UUID.randomUUID(), "Burger King", mock(Address.class), CuisineType.JAPANESE, LocalTime.of(11, 0), LocalTime.of(23, 0), null);

        MenuItem item1 = new MenuItem(item1Id, "Sushi", "Delicious sushi", BigDecimal.valueOf(10.99), false, "/images/burger.png", restaurant1);
        MenuItem item2 = new MenuItem(item2Id, "Burger", "Juicy burger", BigDecimal.valueOf(8.99), false, "/images/burger.png", restaurant2);

        List<MenuItem> menuItems = List.of(item1, item2);

        when(menuItemGateway.findAll()).thenReturn(menuItems);

        // When
        List<MenuItem> result = listMenuItemsUseCase.execute();

        // Then
        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyInAnyOrder(item1, item2);

        verify(menuItemGateway, times(1)).findAll();
    }

    @Test
    void shouldReturnEmptyListIfNoMenuItemsExist() {
        // Given
        when(menuItemGateway.findAll()).thenReturn(Collections.emptyList());

        // When
        List<MenuItem> result = listMenuItemsUseCase.execute();

        // Then
        assertThat(result).isEmpty();

        verify(menuItemGateway, times(1)).findAll();
    }

    @Test
    void shouldHandleErrorsWhenFetchingMenuItems() {
        // Given
        when(menuItemGateway.findAll()).thenThrow(new RuntimeException("Database error"));

        // When / Then
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> listMenuItemsUseCase.execute())
                .withMessage("Database error");

        verify(menuItemGateway, times(1)).findAll();
    }

}
