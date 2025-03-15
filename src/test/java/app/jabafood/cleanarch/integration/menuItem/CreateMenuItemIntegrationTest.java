package app.jabafood.cleanarch.integration.menuItem;

import app.jabafood.cleanarch.application.dto.AddressRequestDTO;
import app.jabafood.cleanarch.application.dto.MenuItemRequestDTO;
import app.jabafood.cleanarch.application.dto.RestaurantRequestDTO;
import app.jabafood.cleanarch.domain.entities.Address;
import app.jabafood.cleanarch.domain.entities.MenuItem;
import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.domain.enums.CuisineType;
import app.jabafood.cleanarch.domain.gateways.IMenuItemGateway;
import app.jabafood.cleanarch.domain.useCases.menuItem.CreateMenuItemUseCase;
import app.jabafood.cleanarch.domain.useCases.restaurant.CreateRestaurantUseCase;
import app.jabafood.cleanarch.infrastructure.persistence.repositories.MenuItemJpaRepository;
import app.jabafood.cleanarch.infrastructure.persistence.repositories.RestaurantJpaRepository;
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

import java.math.BigDecimal;
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
class CreateMenuItemIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MenuItemJpaRepository menuItemJpaRepository;

    @Autowired
    private RestaurantJpaRepository restaurantJpaRepository;

    @Autowired
    private CreateMenuItemUseCase createMenuItemUseCase;

    @Autowired
    private IMenuItemGateway menuItemGateway;

    @Autowired
    private ObjectMapper objectMapper;

    private String url;

    private Restaurant restaurant;
    @Autowired
    private CreateRestaurantUseCase createRestaurantUseCase;

    @BeforeEach
    void setup() {
        url = "api/v1/menu-items/create";

        objectMapper.registerModule(new JavaTimeModule());

        menuItemJpaRepository.deleteAll();
        restaurantJpaRepository.deleteAll();
    }

    @Test
    void shouldCreateMenuItemSuccessfullyThroughController() throws Exception {
        RestaurantRequestDTO restaurant = new RestaurantRequestDTO("Sushi Place", new AddressRequestDTO("Rua Fake", "São Paulo", "SP", "00000-000", "Brazil"), CuisineType.JAPANESE, LocalTime.of(18, 0), LocalTime.of(23, 0), null);

        // Serializa o objeto para JSON
        String restaurantJson = objectMapper.writeValueAsString(restaurant);

        // Realiza a requisição POST para o endpoint da API
        MvcResult result = mockMvc.perform(post("/api/v1/restaurants/create").contentType(MediaType.APPLICATION_JSON)
                        .content(restaurantJson))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse()
                .getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);
        String restaurantId = jsonNode.get("id")
                .asText();

        MenuItemRequestDTO menuItemRequest = new MenuItemRequestDTO("Sushi Roll", "Delicious sushi roll", BigDecimal.valueOf(15.99), false, "/images/burger.png", UUID.fromString(restaurantId));

        String menuItemJson = objectMapper.writeValueAsString(menuItemRequest);

        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(menuItemJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Sushi Roll"))
                .andExpect(jsonPath("$.description").value("Delicious sushi roll"))
                .andExpect(jsonPath("$.price").value(15.99));
    }

    @Test
    void shouldCreateMenuItemSuccessfullyThroughUseCase() {
        Restaurant restaurant = new Restaurant(
                null,
                "Sushi Place",
                new Address(null, "Rua Fake", "São Paulo", "SP", "00000-000", "Brazil"),
                CuisineType.JAPANESE,
                LocalTime.of(18, 0),
                LocalTime.of(23, 0),
                null
                );

                Restaurant createRestaurant = createRestaurantUseCase.execute(restaurant);

        MenuItem menuItem = new MenuItem(null, "Sushi Roll", "Delicious sushi roll", BigDecimal.valueOf(15.99), false, "/images/burger.png", restaurant);

        MenuItem createdMenuItem = createMenuItemUseCase.execute(menuItem);

        assertThat(createdMenuItem).isNotNull();
        assertThat(createdMenuItem.getId()).isNotNull();
        assertThat(createdMenuItem.getName()).isEqualTo("Sushi Roll");
        assertThat(createdMenuItem.getPrice()).isEqualByComparingTo(BigDecimal.valueOf(15.99));
    }

    @Test
    void shouldReturnBadRequestWhenMissingRequiredFields() throws Exception {
        // Criando um JSON inválido sem nome
        String invalidMenuItemJson = """
                {
                    "name": "Sushi",
                    "description": "Delicious sushi roll",
                    "price": 15.99
                }
                """;

        // Realiza a requisição POST para a API
        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                        .content(invalidMenuItemJson))
                .andExpect(status().isBadRequest());
    }
}

