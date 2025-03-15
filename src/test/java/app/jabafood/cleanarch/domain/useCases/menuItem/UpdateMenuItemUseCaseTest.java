package app.jabafood.cleanarch.domain.useCases.menuItem;

import app.jabafood.cleanarch.domain.entities.MenuItem;
import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.domain.exceptions.MenuItemNotFoundException;
import app.jabafood.cleanarch.domain.gateways.IMenuItemGateway;
import app.jabafood.cleanarch.domain.gateways.IRestaurantGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UpdateMenuItemUseCaseTest {

    private UpdateMenuItemUseCase updateMenuItemUseCase;
    private IMenuItemGateway menuItemGateway;
    private IRestaurantGateway restaurantGateway;

    @BeforeEach
    void setup() {
        menuItemGateway = mock(IMenuItemGateway.class);
        restaurantGateway = mock(IRestaurantGateway.class);
        updateMenuItemUseCase = new UpdateMenuItemUseCase(menuItemGateway);
    }

    @Test
    void shouldUpdateMenuItemSuccessfully() {
        // Given
        UUID restaurantId = UUID.randomUUID();
        Restaurant restaurant = new Restaurant(restaurantId, "Pizza Place", null, null, null, null, null);


        UUID menuItemId = UUID.randomUUID();
        MenuItem existingItem = new MenuItem(menuItemId, "Old Sushi", "Delicious old sushi", BigDecimal.valueOf(10.99), false, "/images/burger.png", restaurant);
        MenuItem updatedData = new MenuItem(menuItemId,"New Sushi", "Delicious new sushi", BigDecimal.valueOf(12.99), false, "/images/burger.png", restaurant);

        when(menuItemGateway.findById(menuItemId)).thenReturn(Optional.of(existingItem));
        when(menuItemGateway.save(any(MenuItem.class))).thenReturn(updatedData);

        // When
        MenuItem updatedMenuItem = updateMenuItemUseCase.execute(menuItemId, updatedData);

        // Then
        assertThat(updatedMenuItem).isNotNull();
        assertThat(updatedMenuItem.getName()).isEqualTo("New Sushi");
        assertThat(updatedMenuItem.getDescription()).isEqualTo("Delicious new sushi");
        assertThat(updatedMenuItem.getPrice()).isEqualTo(BigDecimal.valueOf(12.99));

        verify(menuItemGateway, times(1)).save(any(MenuItem.class));
    }

    @Test
    void shouldFailIfMenuItemDoesNotExist() {
        // Given
        UUID id = UUID.randomUUID();
        MenuItem updatedData = new MenuItem(id, "Updated Sushi", "Updated description", BigDecimal.valueOf(15.99), false, "/images/burger.png", null);

        when(menuItemGateway.findById(id)).thenReturn(Optional.empty());

        // When / Then
        MenuItemNotFoundException exception = assertThrows(MenuItemNotFoundException.class, () -> updateMenuItemUseCase.execute(id, updatedData));
        assertThat(exception.getMessage()).isEqualTo("Menu with ID '" + id + "' not found.");
    }

    @Test
    void shouldHandleInvalidPrice() {
        // Given
        UUID id = UUID.randomUUID();
        MenuItem existingItem = new MenuItem(id, "Sushi", "Delicious sushi", BigDecimal.valueOf(10.99), false, "/images/burger.png", null);
        MenuItem updatedData = new MenuItem(id, "Sushi", "Delicious sushi", BigDecimal.valueOf(-5.00), false, "/images/burger.png", null);

        when(menuItemGateway.findById(id)).thenReturn(Optional.of(existingItem));

        // When / Then
        MenuItemNotFoundException exception = assertThrows(MenuItemNotFoundException.class, () -> updateMenuItemUseCase.execute(id, updatedData));
        assertThat(exception.getMessage()).isEqualTo("Menu with ID" + id + " not found.");
    }

}

