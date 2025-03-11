package app.jabafood.cleanarch.domain.entities;

import app.jabafood.cleanarch.domain.enums.CuisineType;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RestaurantTest {

    @Test
    void shouldCreateRestaurantSuccessfully() {
        // Given
        UUID id = UUID.randomUUID();
        String name = "Pizza Express";
        CuisineType cuisineType = CuisineType.PIZZERIA;
        LocalTime openingTime = LocalTime.of(10, 0);
        LocalTime closingTime = LocalTime.of(23, 0);

        UUID ownerId = UUID.randomUUID();
        User owner = new User(ownerId, null, null, null, null, null, null, null);

        // When
        Restaurant restaurant = new Restaurant(id, name, null, cuisineType, openingTime, closingTime, owner);

        // Then
        assertThat(restaurant).isNotNull();
        assertThat(restaurant.getId()).isEqualTo(id);
        assertThat(restaurant.getName()).isEqualTo(name);
        assertThat(restaurant.getCuisineType()).isEqualTo(cuisineType);
        assertThat(restaurant.getOpeningTime()).isEqualTo(openingTime);
        assertThat(restaurant.getClosingTime()).isEqualTo(closingTime);
        assertThat(restaurant.getOwner()).isEqualTo(owner);
    }

    @Test
    void shouldFailWhenClosingTimeIsBeforeOpeningTime() {
        // Given
        UUID id = UUID.randomUUID();
        String name = "Burger House";
        CuisineType cuisineType = CuisineType.BURGER;
        LocalTime openingTime = LocalTime.of(22, 0);
        LocalTime closingTime = LocalTime.of(10, 0);

        UUID ownerId = UUID.randomUUID();
        User owner = new User(ownerId, null, null, null, null, null, null, null);

        // When / Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                new Restaurant(id, name, null, cuisineType, openingTime, closingTime, owner)
        );

        assertThat(exception.getMessage()).isEqualTo("Closing time must be after opening time.");
    }

    @Test
    void shouldUpdateRestaurantWithCopyWith() {
        // Given
        UUID id = UUID.randomUUID();

        UUID ownerId = UUID.randomUUID();
        User owner = new User(ownerId, null, null, null, null, null, null, null);

        Restaurant restaurant = new Restaurant(id, "Old Name", null, CuisineType.JAPANESE, LocalTime.of(10, 0), LocalTime.of(22, 0), owner);

        // When
        Restaurant updatedRestaurant = restaurant.copyWith("New Name", null, CuisineType.ITALIAN, LocalTime.of(9, 0), LocalTime.of(21, 0), restaurant.getOwner());

        // Then
        assertThat(updatedRestaurant.getName()).isEqualTo("New Name");
        assertThat(updatedRestaurant.getCuisineType()).isEqualTo(CuisineType.ITALIAN);
        assertThat(updatedRestaurant.getOpeningTime()).isEqualTo(LocalTime.of(9, 0));
        assertThat(updatedRestaurant.getClosingTime()).isEqualTo(LocalTime.of(21, 0));
    }
}
