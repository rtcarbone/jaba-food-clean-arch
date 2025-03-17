package app.jabafood.cleanarch.domain.entities;

import app.jabafood.cleanarch.domain.exceptions.MenuItemMandatoryFieldException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class MenuItemTest {

    private static final UUID MENU_ITEM_ID = UUID.randomUUID();
    private static final String MENU_ITEM_NAME = "Pizza Margherita";
    private static final String MENU_ITEM_DESC = "Deliciosa pizza italiana com molho de tomate, mussarela e manjericão fresco.";
    private static final BigDecimal MENU_ITEM_PRICE = BigDecimal.valueOf(35.90);
    private static final Boolean MENU_ITEM_IN_RESTAURANT_ONLY = true;
    private static final String MENU_ITEM_IMAGE_PATH = "/images/pizza.png";

    @Mock
    private Restaurant restaurant;

    private MenuItem validMenuItem;

    @BeforeEach
    void setup() {
        validMenuItem = new MenuItem(
                MENU_ITEM_ID, MENU_ITEM_NAME, MENU_ITEM_DESC, MENU_ITEM_PRICE,
                MENU_ITEM_IN_RESTAURANT_ONLY, MENU_ITEM_IMAGE_PATH, restaurant
        );
    }

    @Test
    void shouldCreateMenuItemSuccessfully() {
        assertAll("Validating menu item fields",
                  () -> assertThat(validMenuItem).isNotNull(),
                  () -> assertThat(validMenuItem.getId()).isEqualTo(MENU_ITEM_ID),
                  () -> assertThat(validMenuItem.getName()).isEqualTo(MENU_ITEM_NAME),
                  () -> assertThat(validMenuItem.getDescription()).isEqualTo(MENU_ITEM_DESC),
                  () -> assertThat(validMenuItem.getPrice()).isEqualTo(MENU_ITEM_PRICE),
                  () -> assertThat(validMenuItem.getInRestaurantOnly()).isEqualTo(MENU_ITEM_IN_RESTAURANT_ONLY),
                  () -> assertThat(validMenuItem.getImagePath()).isEqualTo(MENU_ITEM_IMAGE_PATH),
                  () -> assertThat(validMenuItem.getRestaurant()).isEqualTo(restaurant)
        );
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        assertThrows(MenuItemMandatoryFieldException.class, () ->
                new MenuItem(MENU_ITEM_ID, null, MENU_ITEM_DESC, MENU_ITEM_PRICE,
                             MENU_ITEM_IN_RESTAURANT_ONLY, MENU_ITEM_IMAGE_PATH, restaurant).validate()
        );
    }

    @Test
    void shouldThrowExceptionWhenDescriptionIsNull() {
        assertThrows(MenuItemMandatoryFieldException.class, () ->
                new MenuItem(MENU_ITEM_ID, MENU_ITEM_NAME, null, MENU_ITEM_PRICE,
                             MENU_ITEM_IN_RESTAURANT_ONLY, MENU_ITEM_IMAGE_PATH, restaurant).validate()
        );
    }

    @Test
    void shouldThrowExceptionWhenPriceIsNull() {
        assertThrows(MenuItemMandatoryFieldException.class, () ->
                new MenuItem(MENU_ITEM_ID, MENU_ITEM_NAME, MENU_ITEM_DESC, null,
                             MENU_ITEM_IN_RESTAURANT_ONLY, MENU_ITEM_IMAGE_PATH, restaurant).validate()
        );
    }

    @Test
    void shouldThrowExceptionWhenInRestaurantOnlyIsNull() {
        assertThrows(MenuItemMandatoryFieldException.class, () ->
                new MenuItem(MENU_ITEM_ID, MENU_ITEM_NAME, MENU_ITEM_DESC, MENU_ITEM_PRICE,
                             null, MENU_ITEM_IMAGE_PATH, restaurant).validate()
        );
    }

    @Test
    void shouldThrowExceptionWhenImagePathIsNull() {
        assertThrows(MenuItemMandatoryFieldException.class, () ->
                new MenuItem(MENU_ITEM_ID, MENU_ITEM_NAME, MENU_ITEM_DESC, MENU_ITEM_PRICE,
                             MENU_ITEM_IN_RESTAURANT_ONLY, null, restaurant).validate()
        );
    }

    @Test
    void shouldThrowExceptionWhenRestaurantIsNull() {
        assertThrows(MenuItemMandatoryFieldException.class, () ->
                new MenuItem(MENU_ITEM_ID, MENU_ITEM_NAME, MENU_ITEM_DESC, MENU_ITEM_PRICE,
                             MENU_ITEM_IN_RESTAURANT_ONLY, MENU_ITEM_IMAGE_PATH, null).validate()
        );
    }

    @Test
    void shouldUpdateMenuItemWithCopyWith() {
        MenuItem updatedMenuItem = validMenuItem.copyWith(
                "Burger", "New Desc", BigDecimal.valueOf(20.0), false, "new_path", restaurant
        );

        assertAll("Updated menu item fields",
                  () -> assertThat(updatedMenuItem.getName()).isEqualTo("Burger"),
                  () -> assertThat(updatedMenuItem.getDescription()).isEqualTo("New Desc"),
                  () -> assertThat(updatedMenuItem.getPrice()).isEqualTo(BigDecimal.valueOf(20.0)),
                  () -> assertThat(updatedMenuItem.getInRestaurantOnly()).isFalse(),
                  () -> assertThat(updatedMenuItem.getImagePath()).isEqualTo("new_path"),
                  () -> assertThat(updatedMenuItem.getRestaurant()).isEqualTo(restaurant)
        );
    }
}
