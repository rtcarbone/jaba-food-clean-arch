package app.jabafood.cleanarch.domain.useCases.menuItem;

import app.jabafood.cleanarch.domain.entities.Address;
import app.jabafood.cleanarch.domain.entities.MenuItem;
import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.domain.enums.CuisineType;
import app.jabafood.cleanarch.domain.exceptions.MenuItemNotFoundException;
import app.jabafood.cleanarch.domain.gateways.IMenuItemGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

class GetMenuItemByIdUseCaseTest {

    private GetMenuItemByIdUseCase getMenuItemByIdUseCase;
    private IMenuItemGateway menuItemGateway;

    @BeforeEach
    void setup() {
        menuItemGateway = mock(IMenuItemGateway.class);
        getMenuItemByIdUseCase = new GetMenuItemByIdUseCase(menuItemGateway);
    }

    @Test
    void shouldReturnMenuItemIfExists() {
        // Given
        Restaurant restaurant = new Restaurant(UUID.randomUUID(), "Pizza Express", mock(Address.class), CuisineType.JAPANESE, null, null, null);
        UUID id = UUID.randomUUID();
        MenuItem menuItem = new MenuItem(id, "Pizza Margherita", "Tomato, mozzarella, and basil", new BigDecimal("29.99"),false, "/images/burger.png", restaurant);

        when(menuItemGateway.findById(id)).thenReturn(Optional.of(menuItem));

        // When
        MenuItem result = getMenuItemByIdUseCase.execute(id);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getName()).isEqualTo("Pizza Margherita");

        verify(menuItemGateway, times(1)).findById(id);
    }

    @Test
    void shouldThrowExceptionIfMenuItemNotFound() {
        // Given
        UUID id = UUID.randomUUID();
        when(menuItemGateway.findById(id)).thenReturn(Optional.empty());

        // When / Then
        assertThatExceptionOfType(MenuItemNotFoundException.class)
                .isThrownBy(() -> getMenuItemByIdUseCase.execute(id))
                .withMessage("Menu item with ID '" + id + "' not found.");

        verify(menuItemGateway, times(1)).findById(id);
    }
}
