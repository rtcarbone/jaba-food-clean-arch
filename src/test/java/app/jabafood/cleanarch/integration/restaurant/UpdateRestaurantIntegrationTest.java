package app.jabafood.cleanarch.integration.restaurant;

import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.domain.entities.User;
import app.jabafood.cleanarch.domain.enums.CuisineType;
import app.jabafood.cleanarch.domain.enums.UserType;
import app.jabafood.cleanarch.domain.gateways.IRestaurantGateway;
import app.jabafood.cleanarch.domain.useCases.restaurant.UpdateRestaurantUseCase;
import app.jabafood.cleanarch.infrastructure.persistence.entities.RestaurantEntity;
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

import java.time.LocalTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class UpdateRestaurantIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestaurantJpaRepository restaurantJpaRepository;

    @Autowired
    private UpdateRestaurantUseCase updateRestaurantUseCase;

    @Autowired
    private IRestaurantGateway restaurantGateway;

    @Autowired
    private ObjectMapper objectMapper;

    private UUID restaurantId;

    private String url;

    @BeforeEach
    void setup() {
        restaurantJpaRepository.deleteAll(); // Limpa o banco antes de cada teste

        url = "/api/v1/restaurants/{id}";

        // Criando um restaurante para teste
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(UUID.randomUUID());
        restaurantEntity.setName("Original Name");
        restaurantEntity.setCuisineType(CuisineType.PIZZERIA);
        restaurantEntity.setOpeningTime(LocalTime.of(10, 0));
        restaurantEntity.setClosingTime(LocalTime.of(22, 0));
        restaurantJpaRepository.save(restaurantEntity);

        restaurantId = restaurantEntity.getId();
    }

    @Test
    void shouldUpdateRestaurantSuccessfullyThroughController() throws Exception {
        // Criando um JSON com os novos dados
        String updatedRestaurantJson = """
                    {
                        "name": "Updated Name",
                        "cuisineType": "ITALIANA",
                        "openingTime": "09:00:00",
                        "closingTime": "23:00:00"
                    }
                """;

        // Realiza a requisição PUT para atualizar o restaurante
        mockMvc.perform(put(url, restaurantId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(updatedRestaurantJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"))
                .andExpect(jsonPath("$.cuisineType").value("ITALIANA"))
                .andExpect(jsonPath("$.openingTime").value("09:00:00"))
                .andExpect(jsonPath("$.closingTime").value("23:00:00"));
    }

    @Test
    void shouldUpdateRestaurantSuccessfullyThroughUseCase() {
        // Criando um novo objeto Restaurante
        User owner = new User(UUID.randomUUID(), "John Doe", "johndoe", "john@example.com", "password", UserType.RESTAURANT_OWNER, null, null);
        Restaurant updatedRestaurant = new Restaurant(
                restaurantId, "Updated Name", null, CuisineType.ITALIAN,
                LocalTime.of(9, 0), LocalTime.of(23, 0), owner
        );

        // Usa o use case diretamente
        Restaurant result = updateRestaurantUseCase.execute(restaurantId, updatedRestaurant);

        // Valida que os dados retornados estão corretos
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(restaurantId);
        assertThat(result.getName()).isEqualTo("Updated Name");
        assertThat(result.getCuisineType()).isEqualTo(CuisineType.ITALIAN);
    }

    @Test
    void shouldReturnNotFoundWhenRestaurantDoesNotExist() throws Exception {
        UUID nonExistentId = UUID.randomUUID();

        String updatedRestaurantJson = """
                    {
                        "name": "New Name",
                        "cuisineType": "JAPANESE",
                        "openingTime": "08:00:00",
                        "closingTime": "22:00:00"
                    }
                """;

        // Realiza a requisição PUT para um ID inexistente
        mockMvc.perform(put(url, nonExistentId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(updatedRestaurantJson))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnBadRequestWhenClosingTimeIsBeforeOpeningTime() throws Exception {
        String invalidRestaurantJson = """
                    {
                        "name": "Invalid Time",
                        "cuisineType": "MEXICAN",
                        "openingTime": "22:00:00",
                        "closingTime": "10:00:00"
                    }
                """;

        // Realiza a requisição PUT para um horário inválido
        mockMvc.perform(put(url, restaurantId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(invalidRestaurantJson))
                .andExpect(status().isBadRequest());
    }
}
