package app.jabafood.cleanarch.domain.useCases.restaurant;

import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.domain.entities.User;
import app.jabafood.cleanarch.domain.enums.CuisineType;
import app.jabafood.cleanarch.domain.enums.UserType;
import app.jabafood.cleanarch.domain.exceptions.InvalidClosingTimeException;
import app.jabafood.cleanarch.domain.exceptions.RestaurantNotFoundException;
import app.jabafood.cleanarch.domain.exceptions.RestaurantOwnerInvalidException;
import app.jabafood.cleanarch.domain.exceptions.UserNotFoundException;
import app.jabafood.cleanarch.domain.gateways.IRestaurantGateway;
import app.jabafood.cleanarch.domain.gateways.IUserGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UpdateRestaurantUseCaseTest {

    private UpdateRestaurantUseCase updateRestaurantUseCase;
    private IRestaurantGateway restaurantGateway;
    private IUserGateway userGateway;

    @BeforeEach
    void setup() {
        restaurantGateway = mock(IRestaurantGateway.class);
        userGateway = mock(IUserGateway.class);
        updateRestaurantUseCase = new UpdateRestaurantUseCase(restaurantGateway);
    }

    @Test
    void shouldUpdateRestaurantSuccessfully() {
        // Given
        UUID ownerId = UUID.randomUUID();
        User owner = new User(ownerId, "John Doe", "johndoe", "john@example.com", "password", UserType.RESTAURANT_OWNER, null, null);

        UUID restaurantId = UUID.randomUUID();
        Restaurant existingRestaurant = new Restaurant(
                restaurantId, "Old Name", null, CuisineType.PIZZERIA,
                LocalTime.of(10, 0), LocalTime.of(22, 0), owner
        );

        Restaurant updatedData = new Restaurant(
                restaurantId, "New Name", null, CuisineType.ITALIAN,
                LocalTime.of(9, 0), LocalTime.of(23, 0), ownerId, null
        );

        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.of(existingRestaurant));
        when(userGateway.findById(ownerId)).thenReturn(Optional.of(owner));
        when(restaurantGateway.save(any(Restaurant.class))).thenReturn(updatedData);

        // When
        Restaurant updatedRestaurant = updateRestaurantUseCase.execute(restaurantId, updatedData);

        // Then
        assertThat(updatedRestaurant).isNotNull();
        assertThat(updatedRestaurant.getName()).isEqualTo("New Name");
        assertThat(updatedRestaurant.getCuisineType()).isEqualTo(CuisineType.ITALIANA);
        assertThat(updatedRestaurant.getOpeningTime()).isEqualTo(LocalTime.of(9, 0));
        assertThat(updatedRestaurant.getClosingTime()).isEqualTo(LocalTime.of(23, 0));

        verify(restaurantGateway, times(1)).save(any(Restaurant.class));
    }

    @Test
    void shouldFailIfRestaurantDoesNotExist() {
        // Given
        UUID restaurantId = UUID.randomUUID();
        Restaurant updatedData = new Restaurant(
                restaurantId, "Updated Name", null, CuisineType.HAMBURGUERIA,
                LocalTime.of(9, 0), LocalTime.of(23, 0), UUID.randomUUID(), null
        );

        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.empty());

        // When / Then
        RestaurantNotFoundException exception = assertThrows(RestaurantNotFoundException.class, () -> updateRestaurantUseCase.execute(restaurantId, updatedData));
        assertThat(exception.getMessage()).isEqualTo("Restaurant not found.");
    }

    @Test
    void shouldFailIfOwnerDoesNotExist() {
        // Given
        UUID ownerId = UUID.randomUUID();
        UUID restaurantId = UUID.randomUUID();
        Restaurant existingRestaurant = new Restaurant(restaurantId, "Old Name", null, CuisineType.JAPONESA, LocalTime.of(10, 0), LocalTime.of(22, 0), ownerId, null);
        Restaurant updatedData = new Restaurant(restaurantId, "Updated Name", null, CuisineType.JAPONESA, LocalTime.of(9, 0), LocalTime.of(23, 0), ownerId, null);

        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.of(existingRestaurant));
        when(userGateway.findById(ownerId)).thenReturn(Optional.empty());

        // When / Then
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> updateRestaurantUseCase.execute(restaurantId, updatedData));
        assertThat(exception.getMessage()).isEqualTo("User not found.");
    }

    @Test
    void shouldFailIfOwnerIsNotRestaurantOwner() {
        // Given
        UUID ownerId = UUID.randomUUID();
        UUID restaurantId = UUID.randomUUID();
        User user = new User(ownerId, "Jane Doe", "janedoe", "jane@example.com", "password", UserType.CUSTOMER, null);
        Restaurant existingRestaurant = new Restaurant(restaurantId, "Old Name", null, CuisineType.PIZZERIA, LocalTime.of(10, 0), LocalTime.of(22, 0), ownerId, null);
        Restaurant updatedData = new Restaurant(restaurantId, "Updated Name", null, CuisineType.PIZZERIA, LocalTime.of(9, 0), LocalTime.of(23, 0), ownerId, null);

        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.of(existingRestaurant));
        when(userGateway.findById(ownerId)).thenReturn(Optional.of(user));

        // When / Then
        RestaurantOwnerInvalidException exception = assertThrows(RestaurantOwnerInvalidException.class, () -> updateRestaurantUseCase.execute(restaurantId, updatedData));
        assertThat(exception.getMessage()).isEqualTo("User is not a restaurant owner.");
    }

    @Test
    void shouldFailIfClosingTimeIsBeforeOpeningTime() {
        // Given
        UUID ownerId = UUID.randomUUID();
        UUID restaurantId = UUID.randomUUID();
        User owner = new User(ownerId, "John Doe", "johndoe", "john@example.com", "password", UserType.RESTAURANT_OWNER, null);
        Restaurant existingRestaurant = new Restaurant(restaurantId, "Old Name", null, CuisineType.PIZZERIA, LocalTime.of(10, 0), LocalTime.of(22, 0), ownerId, null);
        Restaurant updatedData = new Restaurant(restaurantId, "Updated Name", null, CuisineType.PIZZERIA, LocalTime.of(18, 0), LocalTime.of(17, 0), ownerId, null);

        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.of(existingRestaurant));
        when(userGateway.findById(ownerId)).thenReturn(Optional.of(owner));

        // When / Then
        InvalidClosingTimeException exception = assertThrows(InvalidClosingTimeException.class, () -> updateRestaurantUseCase.execute(restaurantId, updatedData));
        assertThat(exception.getMessage()).isEqualTo("Closing time must be after opening time.");
    }
}