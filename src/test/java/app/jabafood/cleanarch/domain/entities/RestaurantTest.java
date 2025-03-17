package app.jabafood.cleanarch.domain.entities;

import app.jabafood.cleanarch.domain.enums.CuisineType;
import app.jabafood.cleanarch.domain.enums.UserType;
import app.jabafood.cleanarch.domain.exceptions.InvalidClosingTimeException;
import app.jabafood.cleanarch.domain.exceptions.RestaurantMandatoryFieldException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RestaurantTest {

    private static final UUID RESTAURANT_ID = UUID.randomUUID();
    private static final String RESTAURANT_NAME = "Pizza Express";
    private static final CuisineType CUISINE_TYPE = CuisineType.PIZZERIA;
    private static final LocalTime OPENING_TIME = LocalTime.of(10, 0);
    private static final LocalTime CLOSING_TIME = LocalTime.of(23, 0);

    @Mock
    private Address address;

    private User owner;

    private Restaurant restaurant;

    @BeforeEach
    void setup() {
        owner = new User(UUID.randomUUID(), "John Doe", "johndoe", "john@example.com", "password", UserType.RESTAURANT_OWNER, null, null);
        restaurant = new Restaurant(RESTAURANT_ID, RESTAURANT_NAME, address, CUISINE_TYPE, OPENING_TIME, CLOSING_TIME, owner);
    }

    @Test
    void shouldCreateRestaurantSuccessfully() {
        assertAll("Validating restaurant fields",
                  () -> assertThat(restaurant).isNotNull(),
                  () -> assertThat(restaurant.getId()).isEqualTo(RESTAURANT_ID),
                  () -> assertThat(restaurant.getName()).isEqualTo(RESTAURANT_NAME),
                  () -> assertThat(restaurant.getCuisineType()).isEqualTo(CUISINE_TYPE),
                  () -> assertThat(restaurant.getOpeningTime()).isEqualTo(OPENING_TIME),
                  () -> assertThat(restaurant.getClosingTime()).isEqualTo(CLOSING_TIME),
                  () -> assertThat(restaurant.getAddress()).isEqualTo(address),
                  () -> assertThat(restaurant.getOwner()).isEqualTo(owner)
        );
    }

    @Test
    void shouldFailWhenClosingTimeIsBeforeOpeningTime() {
        Restaurant invalidRestaurant = new Restaurant(RESTAURANT_ID, RESTAURANT_NAME, address, CUISINE_TYPE,
                                                      LocalTime.of(22, 0), LocalTime.of(10, 0), owner);

        InvalidClosingTimeException exception = assertThrows(InvalidClosingTimeException.class, invalidRestaurant::validate);
        assertThat(exception.getMessage()).isEqualTo("Closing time must be later than opening time.");
    }

    @Test
    void shouldThrowExceptionWhenNameIsBlank() {
        Restaurant invalidRestaurant = new Restaurant(RESTAURANT_ID, " ", address, CUISINE_TYPE, OPENING_TIME, CLOSING_TIME, owner);
        assertThrows(RestaurantMandatoryFieldException.class, invalidRestaurant::validate);
    }

    @Test
    void shouldThrowExceptionWhenAddressIsNull() {
        Restaurant invalidRestaurant = new Restaurant(RESTAURANT_ID, RESTAURANT_NAME, null, CUISINE_TYPE, OPENING_TIME, CLOSING_TIME, owner);
        assertThrows(RestaurantMandatoryFieldException.class, invalidRestaurant::validate);
    }

    @Test
    void shouldThrowExceptionWhenCuisineTypeIsNull() {
        Restaurant invalidRestaurant = new Restaurant(RESTAURANT_ID, RESTAURANT_NAME, address, null, OPENING_TIME, CLOSING_TIME, owner);
        assertThrows(RestaurantMandatoryFieldException.class, invalidRestaurant::validate);
    }

    @Test
    void shouldThrowExceptionWhenOpeningTimeIsNull() {
        Restaurant invalidRestaurant = new Restaurant(RESTAURANT_ID, RESTAURANT_NAME, address, CUISINE_TYPE, null, CLOSING_TIME, owner);
        assertThrows(RestaurantMandatoryFieldException.class, invalidRestaurant::validate);
    }

    @Test
    void shouldThrowExceptionWhenClosingTimeIsNull() {
        Restaurant invalidRestaurant = new Restaurant(RESTAURANT_ID, RESTAURANT_NAME, address, CUISINE_TYPE, OPENING_TIME, null, owner);
        assertThrows(RestaurantMandatoryFieldException.class, invalidRestaurant::validate);
    }

    @Test
    void shouldThrowExceptionWhenOwnerIsNull() {
        Restaurant invalidRestaurant = new Restaurant(RESTAURANT_ID, RESTAURANT_NAME, address, CUISINE_TYPE, OPENING_TIME, CLOSING_TIME, null);
        assertThrows(RestaurantMandatoryFieldException.class, invalidRestaurant::validate);
    }

    @Test
    void shouldCallAddressValidation() {
        Restaurant restaurantWithMockedAddress = new Restaurant(RESTAURANT_ID, "Taco Place", address, CUISINE_TYPE, OPENING_TIME, CLOSING_TIME, owner);

        restaurantWithMockedAddress.validate();

        verify(address, times(1)).validate();
    }

    @Test
    void shouldUpdateRestaurantWithCopyWith() {
        Restaurant updatedRestaurant = restaurant.copyWith(
                "New Name", address, CuisineType.ITALIAN,
                LocalTime.of(9, 0), LocalTime.of(21, 0), owner
        );

        assertAll("Updated restaurant fields",
                  () -> assertThat(updatedRestaurant.getName()).isEqualTo("New Name"),
                  () -> assertThat(updatedRestaurant.getCuisineType()).isEqualTo(CuisineType.ITALIAN),
                  () -> assertThat(updatedRestaurant.getOpeningTime()).isEqualTo(LocalTime.of(9, 0)),
                  () -> assertThat(updatedRestaurant.getClosingTime()).isEqualTo(LocalTime.of(21, 0)),
                  () -> assertThat(updatedRestaurant.getOwner()).isEqualTo(owner)
        );
    }
}

