package app.jabafood.cleanarch.domain.useCases.menuItem;

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

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

class DeleteMenuItemUseCaseTest {

    private DeleteMenuItemUseCase deleteMenuItemUseCase;
    private IMenuItemGateway menuItemGateway;

    @BeforeEach
    void setup() {
        menuItemGateway = mock(IMenuItemGateway.class);
        deleteMenuItemUseCase = new DeleteMenuItemUseCase(menuItemGateway);
    }

    @Test
    void shouldDeleteMenuItemSuccessfully() {
        // Given
        UUID menuItemId = UUID.randomUUID();
        Restaurant restaurant = new Restaurant(
                UUID.randomUUID(),
                "Pizza Express",
                null,
                CuisineType.ITALIAN,
                LocalTime.of(11, 0),
                LocalTime.of(23, 0),
                null
        );
        MenuItem menuItem = new MenuItem(menuItemId, "Margherita Pizza", "Classic pizza with tomatoes and cheese", BigDecimal.valueOf(12.99), false, "/images/pizza.png", restaurant);

        when(menuItemGateway.findById(menuItemId)).thenReturn(Optional.of(menuItem));

        // When / Then
        assertDoesNotThrow(() -> deleteMenuItemUseCase.execute(menuItemId));
        verify(menuItemGateway, times(1)).delete(menuItemId);
    }

    @Test
    void shouldFailIfMenuItemDoesNotExist() {
        // Given
        UUID menuItemId = UUID.randomUUID();
        when(menuItemGateway.findById(menuItemId)).thenReturn(Optional.empty());

        // When / Then
        assertThatExceptionOfType(MenuItemNotFoundException.class)
                .isThrownBy(() -> deleteMenuItemUseCase.execute(menuItemId))
                .withMessage("Menu with ID '" + menuItemId + "' not found.");

        verify(menuItemGateway, never()).delete(any(UUID.class));
    }
}

