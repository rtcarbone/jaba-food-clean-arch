package app.jabafood.cleanarch.domain.entities;

import app.jabafood.cleanarch.domain.enums.CuisineType;
import app.jabafood.cleanarch.domain.exceptions.InvalidClosingTimeException;
import app.jabafood.cleanarch.domain.exceptions.RestaurantMandatoryFieldException;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class RestaurantTest {

    @Test
    void shouldCreateRestaurantSuccessfully() {
        // Given
        UUID id = UUID.randomUUID();
        String name = "Pizza Express";
        CuisineType cuisineType = CuisineType.PIZZERIA;
        LocalTime openingTime = LocalTime.of(10, 0);
        LocalTime closingTime = LocalTime.of(23, 0);
        Address address = mock(Address.class);
        User owner = mock(User.class);

        // When
        Restaurant restaurant = new Restaurant(id, name, address, cuisineType, openingTime, closingTime, owner);

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
        User owner = mock(User.class);

        Restaurant restaurant = new Restaurant(id, name, mock(Address.class), cuisineType, openingTime, closingTime, owner);

        // When / Then
        InvalidClosingTimeException exception = assertThrows(InvalidClosingTimeException.class, restaurant::validate);
        assertThat(exception.getMessage()).isEqualTo("Closing time must be later than opening time.");
    }

    @Test
    void shouldThrowExceptionWhenNameIsBlank() {
        // Given
        Restaurant restaurant = new Restaurant(UUID.randomUUID(), " ", mock(Address.class), CuisineType.PIZZERIA,
                                               LocalTime.of(10, 0), LocalTime.of(23, 0), mock(User.class));

        // When / Then
        assertThrows(RestaurantMandatoryFieldException.class, restaurant::validate);
    }

    @Test
    void shouldThrowExceptionWhenAddressIsNull() {
        // Given
        Restaurant restaurant = new Restaurant(UUID.randomUUID(), "Sushi Bar", null, CuisineType.JAPANESE,
                                               LocalTime.of(12, 0), LocalTime.of(22, 0), mock(User.class));

        // When / Then
        assertThrows(RestaurantMandatoryFieldException.class, restaurant::validate);
    }

    @Test
    void shouldThrowExceptionWhenCuisineTypeIsNull() {
        // Given
        Restaurant restaurant = new Restaurant(UUID.randomUUID(), "Italian Place", mock(Address.class), null,
                                               LocalTime.of(11, 0), LocalTime.of(23, 0), mock(User.class));

        // When / Then
        assertThrows(RestaurantMandatoryFieldException.class, restaurant::validate);
    }

    @Test
    void shouldThrowExceptionWhenOpeningTimeIsNull() {
        // Given
        Restaurant restaurant = new Restaurant(UUID.randomUUID(), "Mexican Food", mock(Address.class), CuisineType.BURGER,
                                               null, LocalTime.of(22, 0), mock(User.class));

        // When / Then
        assertThrows(RestaurantMandatoryFieldException.class, restaurant::validate);
    }

    @Test
    void shouldThrowExceptionWhenClosingTimeIsNull() {
        // Given
        Restaurant restaurant = new Restaurant(UUID.randomUUID(), "Steak House", mock(Address.class), CuisineType.BURGER,
                                               LocalTime.of(10, 0), null, mock(User.class));

        // When / Then
        assertThrows(RestaurantMandatoryFieldException.class, restaurant::validate);
    }

    @Test
    void shouldThrowExceptionWhenOwnerIsNull() {
        // Given
        Restaurant restaurant = new Restaurant(UUID.randomUUID(), "Vegan Spot", mock(Address.class), CuisineType.JAPANESE,
                                               LocalTime.of(8, 0), LocalTime.of(20, 0), null);

        // When / Then
        assertThrows(RestaurantMandatoryFieldException.class, restaurant::validate);
    }

    @Test
    void shouldCallAddressValidation() {
        //
        User owner = new User(UUID.randomUUID(), null, null, null, null, null, null, null);
        Address address = mock(Address.class);
        Restaurant restaurant = new Restaurant(UUID.randomUUID(), "Taco Place", address, CuisineType.PIZZERIA,
                                               LocalTime.of(10, 0), LocalTime.of(22, 0), owner);

        // When
        restaurant.validate();

        // Then
        verify(address, times(1)).validate();
    }

    @Test
    void shouldUpdateRestaurantWithCopyWith() {
        // Given
        UUID id = UUID.randomUUID();
        UUID ownerId = UUID.randomUUID();
        User owner = new User(ownerId, null, null, null, null, null, null, null);

        Restaurant restaurant = new Restaurant(id, "Old Name", null, CuisineType.JAPANESE,
                                               LocalTime.of(10, 0), LocalTime.of(22, 0), owner);

        // When
        Restaurant updatedRestaurant = restaurant.copyWith("New Name", mock(Address.class), CuisineType.ITALIAN,
                                                           LocalTime.of(9, 0), LocalTime.of(21, 0), owner);

        // Then
        assertThat(updatedRestaurant.getName()).isEqualTo("New Name");
        assertThat(updatedRestaurant.getCuisineType()).isEqualTo(CuisineType.ITALIAN);
        assertThat(updatedRestaurant.getOpeningTime()).isEqualTo(LocalTime.of(9, 0));
        assertThat(updatedRestaurant.getClosingTime()).isEqualTo(LocalTime.of(21, 0));
        assertThat(updatedRestaurant.getOwner()).isEqualTo(owner);
    }

}
