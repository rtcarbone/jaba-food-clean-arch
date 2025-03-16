package app.jabafood.cleanarch.integration.restaurant;

import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.domain.enums.CuisineType;
import app.jabafood.cleanarch.domain.enums.UserType;
import app.jabafood.cleanarch.domain.useCases.restaurant.GetRestaurantByIdUseCase;
import app.jabafood.cleanarch.infrastructure.persistence.entities.AddressEntity;
import app.jabafood.cleanarch.infrastructure.persistence.entities.RestaurantEntity;
import app.jabafood.cleanarch.infrastructure.persistence.entities.UserEntity;
import app.jabafood.cleanarch.infrastructure.persistence.repositories.RestaurantJpaRepository;
import app.jabafood.cleanarch.infrastructure.persistence.repositories.UserJpaRepository;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class GetRestaurantByIdIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestaurantJpaRepository restaurantJpaRepository;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private GetRestaurantByIdUseCase getRestaurantByIdUseCase;

    private String url;

    private UUID restaurantId;

    @BeforeEach
    void setup() {
        url = "/api/v1/restaurants/{id}";

        restaurantJpaRepository.deleteAll();
        userJpaRepository.deleteAll();

        // Criando um dono de restaurante
        UserEntity owner = new UserEntity(null, "John Doe", "johndoe", "john@example.com", "password", UserType.RESTAURANT_OWNER, new AddressEntity(null, "Rua Fake", "São Paulo", "SP", "00000-000", "Brazil", null));
        userJpaRepository.save(owner);

        // Criando um restaurante para teste
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setAddress(new AddressEntity(null, "Rua Fake", "São Paulo", "SP", "00000-000", "Brazil", null));
        restaurantEntity.setName("Pizza Express");
        restaurantEntity.setOpeningTime(LocalTime.of(10, 0));
        restaurantEntity.setClosingTime(LocalTime.of(23, 0));
        restaurantEntity.setCuisineType(CuisineType.PIZZERIA);
        restaurantEntity.setOwner(owner);
        restaurantJpaRepository.save(restaurantEntity);

        restaurantId = restaurantEntity.getId();
    }

    @Test
    void shouldRetrieveRestaurantByIdThroughController() throws Exception {
        // Realiza a requisição GET para o endpoint da API
        mockMvc.perform(get(url, restaurantId)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(restaurantId.toString()))
                .andExpect(jsonPath("$.name").value("Pizza Express"));
    }

    @Test
    void shouldRetrieveRestaurantByIdThroughUseCase() {
        // Usa o use case diretamente
        Restaurant restaurant = getRestaurantByIdUseCase.execute(restaurantId);

        // Valida que os dados retornados estão corretos
        assertThat(restaurant).isNotNull();
        assertThat(restaurant.getId()).isEqualTo(restaurantId);
        assertThat(restaurant.getName()).isEqualTo("Pizza Express");
    }

    @Test
    void shouldReturnNotFoundWhenRestaurantDoesNotExist() throws Exception {
        UUID nonExistentId = UUID.randomUUID();

        // Realiza a requisição GET para um ID inexistente
        mockMvc.perform(get(url, nonExistentId)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
