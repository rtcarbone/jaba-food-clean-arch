package app.jabafood.cleanarch.integration.restaurant;

import app.jabafood.cleanarch.application.dto.AddressRequestDTO;
import app.jabafood.cleanarch.application.dto.RestaurantRequestDTO;
import app.jabafood.cleanarch.application.dto.UserRequestDTO;
import app.jabafood.cleanarch.domain.entities.Address;
import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.domain.entities.User;
import app.jabafood.cleanarch.domain.enums.CuisineType;
import app.jabafood.cleanarch.domain.enums.UserType;
import app.jabafood.cleanarch.domain.gateways.IRestaurantGateway;
import app.jabafood.cleanarch.domain.gateways.IUserGateway;
import app.jabafood.cleanarch.domain.useCases.restaurant.CreateRestaurantUseCase;
import app.jabafood.cleanarch.domain.useCases.user.CreateUserUseCase;
import app.jabafood.cleanarch.infrastructure.persistence.repositories.RestaurantJpaRepository;
import app.jabafood.cleanarch.infrastructure.persistence.repositories.UserJpaRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
class CreateRestaurantIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestaurantJpaRepository restaurantJpaRepository;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private CreateRestaurantUseCase createRestaurantUseCase;

    @Autowired
    private CreateUserUseCase createUserUseCase;

    @Autowired
    private IRestaurantGateway restaurantGateway;

    @Autowired
    private IUserGateway userGateway;

    @Autowired
    private ObjectMapper objectMapper;

    private String url;

    @BeforeEach
    void setup() {
        url = "/api/v1/restaurants/create";

        objectMapper.registerModule(new JavaTimeModule());

        restaurantJpaRepository.deleteAll();
        userJpaRepository.deleteAll();
    }

    @Test
    void shouldCreateRestaurantSuccessfullyThroughController() throws Exception {

        UserRequestDTO owner = new UserRequestDTO("John Doe", "john@example.com", "johndoe", "password", UserType.RESTAURANT_OWNER, new AddressRequestDTO("Rua Fake", "São Paulo", "SP", "00000-000", "Brazil"));

        // Serializa o objeto para JSON
        String ownerJson = objectMapper.writeValueAsString(owner);

        // Realiza a requisição POST para o endpoint da API
        MvcResult result = mockMvc.perform(post("/api/v1/users/create").contentType(MediaType.APPLICATION_JSON)
                                                   .content(ownerJson))
                .andExpect(status().isCreated())
                .andReturn();

        String jsonResponse = result.getResponse()
                .getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);
        String ownerId = jsonNode.get("id")
                .asText();

        RestaurantRequestDTO restaurant = new RestaurantRequestDTO("Sushi Place", new AddressRequestDTO("Rua Fake", "São Paulo", "SP", "00000-000", "Brazil"), CuisineType.JAPANESE, LocalTime.of(18, 0), LocalTime.of(23, 0), UUID.fromString(ownerId));

        String restaurantJson = objectMapper.writeValueAsString(restaurant);

        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                                .content(restaurantJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Sushi Place"))
                .andExpect(jsonPath("$.cuisineType").value("JAPANESE"))
                .andExpect(jsonPath("$.openingTime").value("18:00"))
                .andExpect(jsonPath("$.closingTime").value("23:00"));
    }

    @Test
    void shouldCreateRestaurantSuccessfullyThroughUseCase() {
        User owner = new User(
                null,
                "John Doe",
                "john@example.com",
                "johndoe",
                "password",
                UserType.RESTAURANT_OWNER,
                null,
                new Address(null, "Rua Fake", "123", "São Paulo", "SP", "00000-000")
        );

        User createdOwner = createUserUseCase.execute(owner);

        Restaurant restaurant = new Restaurant(null, "Sushi Place", new Address(null, "Rua Fake", "São Paulo", "SP", "00000-000", "Brazil"), CuisineType.JAPANESE, LocalTime.of(18, 0), LocalTime.of(23, 0), createdOwner);

        Restaurant createdRestaurant = createRestaurantUseCase.execute(restaurant);

        assertThat(createdRestaurant).isNotNull();
        assertThat(createdRestaurant.getId()).isNotNull();
        assertThat(createdRestaurant.getName()).isEqualTo("Sushi Place");
        assertThat(createdRestaurant.getCuisineType()).isEqualTo(CuisineType.JAPANESE);
    }

    @Test
    void shouldReturnBadRequestWhenMissingRequiredFields() throws Exception {
        String invalidRestaurantJson = """
                    {
                        "cuisineType": "JAPANESE",
                        "openingTime": "18:00:00",
                        "closingTime": "23:00:00"
                    }
                """;

        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                                .content(invalidRestaurantJson))
                .andExpect(status().isBadRequest());
    }
}
