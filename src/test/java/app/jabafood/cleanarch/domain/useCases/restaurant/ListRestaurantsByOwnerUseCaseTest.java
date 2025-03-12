package app.jabafood.cleanarch.domain.useCases.restaurant;

import app.jabafood.cleanarch.domain.entities.Address;
import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.domain.entities.User;
import app.jabafood.cleanarch.domain.enums.CuisineType;
import app.jabafood.cleanarch.domain.enums.UserType;
import app.jabafood.cleanarch.domain.gateways.IRestaurantGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListRestaurantsByOwnerUseCaseTest {

    @Mock
    private IRestaurantGateway restaurantGateway;

    @InjectMocks
    private ListRestaurantsByOwnerUseCase listRestaurantsByOwnerUseCase;

    private UUID ownerId;
    private Restaurant restaurant1;
    private Restaurant restaurant2;

    @BeforeEach
    void setup() {
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
        // Arrange
        when(restaurantGateway.findByOwnerId(any(UUID.class))).thenReturn(List.of(restaurant1));

        // Act
        listRestaurantsByOwnerUseCase.execute(ownerId);

        // Assert - Verifica se o método foi chamado com o UUID correto
        verify(restaurantGateway, times(1)).findByOwnerId(ownerId);
    }
}
