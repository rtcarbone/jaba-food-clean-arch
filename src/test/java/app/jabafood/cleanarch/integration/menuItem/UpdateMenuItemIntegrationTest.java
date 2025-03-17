package app.jabafood.cleanarch.integration.menuItem;

import app.jabafood.cleanarch.domain.entities.Address;
import app.jabafood.cleanarch.domain.entities.MenuItem;
import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.domain.entities.User;
import app.jabafood.cleanarch.domain.enums.CuisineType;
import app.jabafood.cleanarch.domain.enums.UserType;
import app.jabafood.cleanarch.domain.useCases.menuItem.UpdateMenuItemUseCase;
import app.jabafood.cleanarch.domain.useCases.restaurant.CreateRestaurantUseCase;
import app.jabafood.cleanarch.domain.useCases.user.CreateUserUseCase;
import app.jabafood.cleanarch.infrastructure.persistence.entities.AddressEntity;
import app.jabafood.cleanarch.infrastructure.persistence.entities.MenuItemEntity;
import app.jabafood.cleanarch.infrastructure.persistence.entities.RestaurantEntity;
import app.jabafood.cleanarch.infrastructure.persistence.entities.UserEntity;
import app.jabafood.cleanarch.infrastructure.persistence.repositories.MenuItemJpaRepository;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
@Transactional
class UpdateMenuItemIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MenuItemJpaRepository menuItemJpaRepository;

    @Autowired
    private RestaurantJpaRepository restaurantJpaRepository;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private UpdateMenuItemUseCase updateMenuItemUseCase;

    @Autowired
    private CreateRestaurantUseCase createRestaurantUseCase;

    @Autowired
    private CreateUserUseCase createUserUseCase;

    private UUID menuItemId;

    private String url;

    @BeforeEach
    void setup() {
        url = "/api/v1/menu-items/{id}/update";

        menuItemJpaRepository.deleteAll();
        restaurantJpaRepository.deleteAll();
        userJpaRepository.deleteAll();

        UserEntity owner = new UserEntity(null, "John Doe", "john.doe@example.com", "johndoe", "123456", UserType.RESTAURANT_OWNER,
                                          new AddressEntity(null, "Rua Fake", "São Paulo", "SP", "00000-000", "Brazil", LocalDateTime.now()));
        userJpaRepository.save(owner);

        RestaurantEntity restaurant = new RestaurantEntity(null, "Sabor Italiano",
                                                           new AddressEntity(null, "Rua Fake", "São Paulo", "SP", "00000-000", "Brazil", LocalDateTime.now()), CuisineType.JAPANESE, LocalTime.of(18, 0), LocalTime.of(23, 0), owner, null, LocalDateTime.now());
        restaurantJpaRepository.save(restaurant);

        MenuItemEntity menuItemEntity = new MenuItemEntity();
        menuItemEntity.setName("Margherita Pizza");
        menuItemEntity.setDescription("Mussarela, parmesão, tomate");
        menuItemEntity.setPrice(BigDecimal.valueOf(20.0));
        menuItemEntity.setInRestaurantOnly(false);
        menuItemEntity.setImagePath("/images/pizza.png");
        menuItemEntity.setRestaurant(restaurant);
        menuItemJpaRepository.save(menuItemEntity);

        menuItemId = menuItemEntity.getId();
    }

    @Test
    void shouldUpdateMenuItemSuccessfullyThroughController() throws Exception {
        String updatedMenuItemJson = """
                    {
                        "name": "Updated Pizza",
                        "price": 25.0
                    }
                """;

        mockMvc.perform(put(url, menuItemId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(updatedMenuItemJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Pizza"))
                .andExpect(jsonPath("$.price").value(25.0));
    }

    @Test
    void shouldUpdateMenuItemSuccessfullyThroughUseCase() {
        User owner = new User(
                null,
                "John Doe",
                "johndoe@example.com",
                "johndoe_login",
                "password",
                UserType.RESTAURANT_OWNER,
                null,
                new Address(null, "Rua Fake", "123", "São Paulo", "SP", "00000-000")
        );

        User createdOwner = createUserUseCase.execute(owner);

        Restaurant restaurant = new Restaurant(null, "Sabor Italiano", new Address(null, "Rua Fake", "São Paulo", "SP", "00000-000", "Brazil"), CuisineType.ITALIAN,
                                               LocalTime.of(9, 0), LocalTime.of(23, 0), createdOwner);

        Restaurant createdRestaurant = createRestaurantUseCase.execute(restaurant);

        MenuItem updatedMenuItem = new MenuItem(
                menuItemId, "Pizza Margharita", null, BigDecimal.valueOf(25.0), false, "/images/pizza.png", createdRestaurant);

        MenuItem result = updateMenuItemUseCase.execute(menuItemId, updatedMenuItem);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(menuItemId);
        assertThat(result.getName()).isEqualTo("Pizza Margharita");
        assertThat(result.getPrice()).isEqualTo(BigDecimal.valueOf(25.0));
    }

    @Test
    void shouldReturnNotFoundWhenMenuItemDoesNotExist() throws Exception {
        UUID nonExistentId = UUID.randomUUID();

        String updatedMenuItemJson = """
                    {
                        "name": "New Item",
                        "price": 15.0
                    }
                """;

        mockMvc.perform(put(url, nonExistentId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(updatedMenuItemJson))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnBadRequestWhenPriceIsNegative() throws Exception {
        String invalidMenuItemJson = """
                    {
                        "name": "Invalid Pizza",
                        "price": -10.0
                    }
                """;

        mockMvc.perform(put(url, menuItemId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(invalidMenuItemJson))
                .andExpect(status().isBadRequest());
    }
}

