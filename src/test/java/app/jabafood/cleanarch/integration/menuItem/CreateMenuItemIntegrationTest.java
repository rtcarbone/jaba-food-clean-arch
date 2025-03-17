package app.jabafood.cleanarch.integration.menuItem;

import app.jabafood.cleanarch.application.dto.MenuItemRequestDTO;
import app.jabafood.cleanarch.domain.entities.Address;
import app.jabafood.cleanarch.domain.entities.MenuItem;
import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.domain.entities.User;
import app.jabafood.cleanarch.domain.enums.CuisineType;
import app.jabafood.cleanarch.domain.enums.UserType;
import app.jabafood.cleanarch.domain.useCases.menuItem.CreateMenuItemUseCase;
import app.jabafood.cleanarch.domain.useCases.restaurant.CreateRestaurantUseCase;
import app.jabafood.cleanarch.domain.useCases.user.CreateUserUseCase;
import app.jabafood.cleanarch.infrastructure.persistence.entities.AddressEntity;
import app.jabafood.cleanarch.infrastructure.persistence.entities.RestaurantEntity;
import app.jabafood.cleanarch.infrastructure.persistence.entities.UserEntity;
import app.jabafood.cleanarch.infrastructure.persistence.repositories.MenuItemJpaRepository;
import app.jabafood.cleanarch.infrastructure.persistence.repositories.RestaurantJpaRepository;
import app.jabafood.cleanarch.infrastructure.persistence.repositories.UserJpaRepository;
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
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
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
    private UserJpaRepository userJpaRepository;

    @Autowired
    private CreateMenuItemUseCase createMenuItemUseCase;

    @Autowired
    private CreateRestaurantUseCase createRestaurantUseCase;

    @Autowired
    private CreateUserUseCase createUserUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    private String url;

    private UUID restaurantId;

    @BeforeEach
    void setup() {
        url = "/api/v1/menu-items/create";

        objectMapper.registerModule(new JavaTimeModule());

        menuItemJpaRepository.deleteAll();
        restaurantJpaRepository.deleteAll();
        userJpaRepository.deleteAll();

        UserEntity owner = new UserEntity(null, "John Doe", "johndoe", "john@example.com", "password", UserType.RESTAURANT_OWNER, new AddressEntity(null, "Rua Fake", "São Paulo", "SP", "00000-000", "Brazil", null));
        userJpaRepository.save(owner);

        RestaurantEntity restaurant = new RestaurantEntity();
        restaurant.setAddress(new AddressEntity(null, "Rua Fake", "São Paulo", "SP", "00000-000", "Brazil", null));
        restaurant.setName("Pizza Express");
        restaurant.setCuisineType(CuisineType.PIZZERIA);
        restaurant.setOpeningTime(LocalTime.of(10, 0));
        restaurant.setClosingTime(LocalTime.of(22, 0));
        restaurant.setOwner(owner);
        restaurantJpaRepository.save(restaurant);

        restaurantId = restaurant.getId();
    }

    @Test
    void shouldCreateMenuItemSuccessfullyThroughController() throws Exception {
        MenuItemRequestDTO menuItemRequest = new MenuItemRequestDTO("Sushi Roll", "Delicious sushi roll", BigDecimal.valueOf(15.99), false, "/images/burger.png", restaurantId);

        String menuItemJson = objectMapper.writeValueAsString(menuItemRequest);

        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                                .content(menuItemJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Sushi Roll"))
                .andExpect(jsonPath("$.description").value("Delicious sushi roll"))
                .andExpect(jsonPath("$.price").value(15.99));
    }

    @Test
    void shouldCreateMenuItemSuccessfullyThroughUseCase() {
        User owner = new User(null, "John Doe", "john@example.com", "johndoe", "password", UserType.RESTAURANT_OWNER, LocalDateTime.now(), mock(Address.class));
        User createdOwner = createUserUseCase.execute(owner);

        Restaurant restaurant = new Restaurant(
                null,
                "Sushi Place",
                new Address(null, "Rua Fake", "São Paulo", "SP", "00000-000", "Brazil"),
                CuisineType.JAPANESE,
                LocalTime.of(18, 0),
                LocalTime.of(23, 0),
                createdOwner
        );
        Restaurant createdRestaurant = createRestaurantUseCase.execute(restaurant);

        MenuItem menuItem = new MenuItem(null, "Sushi Roll", "Delicious sushi roll", BigDecimal.valueOf(15.99), false, "/images/burger.png", createdRestaurant);
        MenuItem createdMenuItem = createMenuItemUseCase.execute(menuItem);

        assertThat(createdMenuItem).isNotNull();
        assertThat(createdMenuItem.getId()).isNotNull();
        assertThat(createdMenuItem.getName()).isEqualTo("Sushi Roll");
        assertThat(createdMenuItem.getPrice()).isEqualByComparingTo(BigDecimal.valueOf(15.99));
    }

    @Test
    void shouldReturnBadRequestWhenMissingRequiredFields() throws Exception {
        String invalidMenuItemJson = """
                {
                    "name": "Sushi",
                    "description": "Delicious sushi roll",
                    "price": 15.99
                }
                """;

        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                                .content(invalidMenuItemJson))
                .andExpect(status().isBadRequest());
    }
}

