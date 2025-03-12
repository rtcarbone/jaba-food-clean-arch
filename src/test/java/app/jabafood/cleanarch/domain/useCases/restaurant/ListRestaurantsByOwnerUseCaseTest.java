package app.jabafood.cleanarch.domain.useCases.restaurant;

import app.jabafood.cleanarch.domain.entities.Address;
import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.domain.entities.User;
import app.jabafood.cleanarch.domain.enums.CuisineType;
import app.jabafood.cleanarch.domain.enums.UserType;
import app.jabafood.cleanarch.domain.exceptions.UserNotFoundException;
import app.jabafood.cleanarch.domain.gateways.IRestaurantGateway;
import app.jabafood.cleanarch.domain.gateways.IUserGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ListRestaurantsByOwnerUseCaseTest {

    private ListRestaurantsByOwnerUseCase listRestaurantsByOwnerUseCase;
    private IRestaurantGateway restaurantGateway;
    private IUserGateway userGateway;

    private UUID ownerId;
    private Restaurant restaurant1;
    private Restaurant restaurant2;

    @BeforeEach
    void setup() {
        restaurantGateway = mock(IRestaurantGateway.class);
        userGateway = mock(IUserGateway.class);
        listRestaurantsByOwnerUseCase = new ListRestaurantsByOwnerUseCase(restaurantGateway, userGateway);

        ownerId = UUID.randomUUID();
        User owner = new User(ownerId, "John Doe", "johndoe", "john@example.com", "password", UserType.RESTAURANT_OWNER, null, null);

        restaurant1 = new Restaurant(
                UUID.randomUUID(),
                "Pizza Express",
                new Address(UUID.randomUUID(), "Rua A", "São Paulo", "SP", "12345-678", "Brasil"),
                CuisineType.PIZZERIA,
                LocalTime.of(10, 0),
                LocalTime.of(22, 0),
                owner
        );

        restaurant2 = new Restaurant(
                UUID.randomUUID(),
                "Sushi House",
                new Address(UUID.randomUUID(), "Rua B", "Rio de Janeiro", "RJ", "54321-876", "Brasil"),
                CuisineType.JAPANESE,
                LocalTime.of(12, 0),
                LocalTime.of(23, 0),
                owner
        );
    }

    @Test
    void shouldReturnRestaurantsByOwnerSuccessfully() {
        // Arrange - Configura o comportamento do Mock
        User owner = new User(ownerId, "John Doe", "john@example.com", "johndoe", "password", UserType.RESTAURANT_OWNER, null, mock(Address.class));

        when(userGateway.findById(ownerId)).thenReturn(Optional.of(owner));
        when(restaurantGateway.findByOwnerId(ownerId)).thenReturn(List.of(restaurant1, restaurant2));

        // Act - Executa o caso de uso
        List<Restaurant> result = listRestaurantsByOwnerUseCase.execute(ownerId);

        // Assert - Verifica se os restaurantes foram retornados corretamente
        assertThat(result).hasSize(2);
        assertThat(result.get(0)
                           .getName()).isEqualTo("Pizza Express");
        assertThat(result.get(1)
                           .getName()).isEqualTo("Sushi House");

        // Verifica se o método foi chamado uma vez com o ownerId correto
        verify(restaurantGateway, times(1)).findByOwnerId(ownerId);
    }

    @Test
    void shouldReturnEmptyListWhenOwnerHasNoRestaurants() {
        // Arrange - Configura o Mock para retornar uma lista vazia
        User owner = new User(ownerId, "John Doe", "john@example.com", "johndoe", "password", UserType.RESTAURANT_OWNER, null, mock(Address.class));

        when(userGateway.findById(ownerId)).thenReturn(Optional.of(owner));
        when(restaurantGateway.findByOwnerId(ownerId)).thenReturn(List.of());

        // Act - Executa o caso de uso
        List<Restaurant> result = listRestaurantsByOwnerUseCase.execute(ownerId);

        // Assert - Verifica que a lista está vazia
        assertThat(result).isEmpty();

        // Verifica se o método foi chamado uma vez com o ownerId correto
        verify(restaurantGateway, times(1)).findByOwnerId(ownerId);
    }

    @Test
    void shouldCallGatewayWithCorrectOwnerId() {
        // Arrange - Criar um usuário e simular que ele existe no userGateway
        User owner = new User(ownerId, "John Doe", "john@example.com", "johndoe", "password", UserType.RESTAURANT_OWNER, null, mock(Address.class));

        when(userGateway.findById(ownerId)).thenReturn(Optional.of(owner));
        when(restaurantGateway.findByOwnerId(ownerId)).thenReturn(List.of(restaurant1));

        // Act - Executa o caso de uso
        listRestaurantsByOwnerUseCase.execute(ownerId);

        // Assert - Verifica se os métodos foram chamados corretamente
        verify(userGateway, times(1)).findById(ownerId);
        verify(restaurantGateway, times(1)).findByOwnerId(ownerId);
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenOwnerDoesNotExist() {
        // Arrange - Configura o Mock para retornar Optional vazio
        when(userGateway.findById(ownerId)).thenReturn(Optional.empty());

        // Act / Assert - Verifica se a exceção é lançada
        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                                                       () -> listRestaurantsByOwnerUseCase.execute(ownerId));

        assertThat(exception.getMessage()).isEqualTo("User with ID '" + ownerId + "' not found.");

        // Verifica que o método de buscar restaurantes **não foi chamado**
        verify(restaurantGateway, never()).findByOwnerId(any(UUID.class));
    }

}
