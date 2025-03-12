package app.jabafood.cleanarch.integration.restaurant;

import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.domain.enums.CuisineType;
import app.jabafood.cleanarch.domain.gateways.IRestaurantGateway;
import app.jabafood.cleanarch.domain.useCases.restaurant.ListRestaurantsUseCase;
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
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ListRestaurantsIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestaurantJpaRepository restaurantJpaRepository;

    @Autowired
    private ListRestaurantsUseCase listRestaurantsUseCase;

    @Autowired
    private IRestaurantGateway restaurantGateway;

    @Autowired
    private ObjectMapper objectMapper;

    private String url;

    @BeforeEach
    void setup() {
        restaurantJpaRepository.deleteAll(); // Limpa o banco antes de cada teste

        url = "/api/v1/restaurants";

        // Criando restaurantes para teste
        RestaurantEntity restaurant1 = new RestaurantEntity();
        restaurant1.setId(UUID.randomUUID());
        restaurant1.setName("Pizza Express");
        restaurant1.setCuisineType(CuisineType.PIZZERIA);
        restaurant1.setOpeningTime(LocalTime.of(10, 0));
        restaurant1.setClosingTime(LocalTime.of(22, 0));

        RestaurantEntity restaurant2 = new RestaurantEntity();
        restaurant2.setId(UUID.randomUUID());
        restaurant2.setName("Burger King");
        restaurant2.setCuisineType(CuisineType.BURGER);
        restaurant2.setOpeningTime(LocalTime.of(11, 0));
        restaurant2.setClosingTime(LocalTime.of(23, 0));

        restaurantJpaRepository.saveAll(List.of(restaurant1, restaurant2));
    }

    @Test
    void shouldListAllRestaurantsSuccessfullyThroughController() throws Exception {
        // Realiza a requisição GET para listar os restaurantes
        mockMvc.perform(get(url)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Pizza Express"))
                .andExpect(jsonPath("$[1].name").value("Burger King"));
    }

    @Test
    void shouldListAllRestaurantsSuccessfullyThroughUseCase() {
        // Usa o use case diretamente
        List<Restaurant> restaurants = listRestaurantsUseCase.execute();

        // Valida que os dados retornados estão corretos
        assertThat(restaurants).isNotEmpty();
        assertThat(restaurants).hasSize(2);
        assertThat(restaurants.get(0)
                           .getName()).isEqualTo("Pizza Express");
        assertThat(restaurants.get(1)
                           .getName()).isEqualTo("Burger King");
    }

    @Test
    void shouldReturnEmptyListWhenNoRestaurantsExist() throws Exception {
        // Limpa os dados do banco de dados antes do teste
        restaurantJpaRepository.deleteAll();

        // Realiza a requisição GET para um banco de dados vazio
        mockMvc.perform(get(url)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));

        // Usa o use case diretamente
        List<Restaurant> restaurants = listRestaurantsUseCase.execute();
        assertThat(restaurants).isEmpty();
    }
}