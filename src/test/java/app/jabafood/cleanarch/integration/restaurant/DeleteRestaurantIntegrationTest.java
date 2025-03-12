package app.jabafood.cleanarch.integration.restaurant;

import app.jabafood.cleanarch.domain.enums.CuisineType;
import app.jabafood.cleanarch.domain.enums.UserType;
import app.jabafood.cleanarch.domain.gateways.IRestaurantGateway;
import app.jabafood.cleanarch.domain.useCases.restaurant.DeleteRestaurantUseCase;
import app.jabafood.cleanarch.infrastructure.persistence.entities.RestaurantEntity;
import app.jabafood.cleanarch.infrastructure.persistence.entities.UserEntity;
import app.jabafood.cleanarch.infrastructure.persistence.repositories.RestaurantJpaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class DeleteRestaurantIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestaurantJpaRepository restaurantJpaRepository;

    @Autowired
    private DeleteRestaurantUseCase deleteRestaurantUseCase;

    @Autowired
    private IRestaurantGateway restaurantGateway;

    @Autowired
    private ObjectMapper objectMapper;

    private UUID restaurantId;

    private String url;

    @BeforeEach
    void setup() {
        restaurantJpaRepository.deleteAll(); // Limpa o banco antes de cada teste

        url = "api/v1/restaurants/{id}/delete";

        // Criando um dono de restaurante
        UserEntity owner = new UserEntity(UUID.randomUUID(), "John Doe", "johndoe", "john@example.com", "password", UserType.RESTAURANT_OWNER, null);

        // Criando um restaurante para teste
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(UUID.randomUUID());
        restaurantEntity.setName("Pizza Express");
        restaurantEntity.setCuisineType(CuisineType.PIZZERIA);
        restaurantEntity.setOwner(owner);
        restaurantJpaRepository.save(restaurantEntity);

        restaurantId = restaurantEntity.getId();
    }

    @Test
    void shouldDeleteRestaurantSuccessfullyThroughController() throws Exception {
        // Realiza a requisição DELETE para excluir o restaurante
        mockMvc.perform(delete(url, restaurantId)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Verifica que o restaurante foi removido
        assertThat(restaurantJpaRepository.findById(restaurantId)).isEmpty();
    }

    @Test
    void shouldDeleteRestaurantSuccessfullyThroughUseCase() {
        // Usa o use case diretamente
        deleteRestaurantUseCase.execute(restaurantId);

        // Verifica que o restaurante foi removido do banco
        assertThat(restaurantJpaRepository.findById(restaurantId)).isEmpty();
    }

    @Test
    void shouldReturnNotFoundWhenRestaurantDoesNotExist() throws Exception {
        UUID nonExistentId = UUID.randomUUID();

        // Realiza a requisição DELETE para um restaurante inexistente
        mockMvc.perform(delete(url, nonExistentId)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
