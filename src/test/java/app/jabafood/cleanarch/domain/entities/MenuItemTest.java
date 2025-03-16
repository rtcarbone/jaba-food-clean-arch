package app.jabafood.cleanarch.domain.entities;

import app.jabafood.cleanarch.domain.exceptions.MenuItemMandatoryFieldException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class MenuItemTest {

    @Test
    void shouldCreateMenuItemSuccessfully() {
        UUID id = UUID.randomUUID();
        String name = "Pizza Margherita";
        String description = "Deliciosa pizza italiana com molho de tomate, mussarela e manjericão fresco.";
        BigDecimal price = BigDecimal.valueOf(35.90);
        Boolean inRestaurantOnly = true;
        String imagePath = "/images/pizza.png";
        Restaurant restaurant = mock(Restaurant.class);

        MenuItem menuItem = new MenuItem(id, name, description, price, inRestaurantOnly, imagePath, restaurant);

        assertThat(menuItem).isNotNull();
        assertThat(menuItem.getId()).isEqualTo(id);
        assertThat(menuItem.getName()).isEqualTo(name);
        assertThat(menuItem.getDescription()).isEqualTo(description);
        assertThat(menuItem.getPrice()).isEqualTo(price);
        assertThat(menuItem.getInRestaurantOnly()).isEqualTo(inRestaurantOnly);
        assertThat(menuItem.getImagePath()).isEqualTo(imagePath);
        assertThat(menuItem.getRestaurant()).isEqualTo(restaurant);
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        assertThrows(MenuItemMandatoryFieldException.class, () ->
                new MenuItem(UUID.randomUUID(), null, "Desc", BigDecimal.TEN, true, "path", mock(Restaurant.class))
        );
    }

    @Test
    void shouldThrowExceptionWhenDescriptionIsNull() {
        assertThrows(MenuItemMandatoryFieldException.class, () ->
                new MenuItem(UUID.randomUUID(), "Pizza", null, BigDecimal.TEN, true, "path", mock(Restaurant.class))
        );
    }

    @Test
    void shouldThrowExceptionWhenPriceIsNull() {
        assertThrows(MenuItemMandatoryFieldException.class, () ->
                new MenuItem(UUID.randomUUID(), "Pizza", "Desc", null, true, "path", mock(Restaurant.class))
        );
    }

    @Test
    void shouldThrowExceptionWhenInRestaurantOnlyIsNull() {
        assertThrows(MenuItemMandatoryFieldException.class, () ->
                new MenuItem(UUID.randomUUID(), "Pizza", "Desc", BigDecimal.TEN, null, "path", mock(Restaurant.class))
        );
    }

    @Test
    void shouldThrowExceptionWhenImagePathIsNull() {
        assertThrows(MenuItemMandatoryFieldException.class, () ->
                new MenuItem(UUID.randomUUID(), "Pizza", "Desc", BigDecimal.TEN, true, null, mock(Restaurant.class))
        );
    }

    @Test
    void shouldThrowExceptionWhenRestaurantIsNull() {
        assertThrows(MenuItemMandatoryFieldException.class, () ->
                new MenuItem(UUID.randomUUID(), "Pizza", "Desc", BigDecimal.TEN, true, "path", null)
        );
    }

    @Test
    void shouldUpdateMenuItemWithCopyWith() {
        UUID id = UUID.randomUUID();
        Restaurant restaurant = mock(Restaurant.class);

        MenuItem menuItem = new MenuItem(id, "Pizza", "Desc", BigDecimal.TEN, true, "path", restaurant);
        MenuItem updatedMenuItem = menuItem.copyWith("Burger", "New Desc", BigDecimal.valueOf(20.0), false, "new_path", restaurant);

        assertThat(updatedMenuItem.getName()).isEqualTo("Burger");
        assertThat(updatedMenuItem.getDescription()).isEqualTo("New Desc");
        assertThat(updatedMenuItem.getPrice()).isEqualTo(BigDecimal.valueOf(20.0));
        assertThat(updatedMenuItem.getInRestaurantOnly()).isFalse();
        assertThat(updatedMenuItem.getImagePath()).isEqualTo("new_path");
    }
}
