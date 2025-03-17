package app.jabafood.cleanarch.integration.menuItem;

import app.jabafood.cleanarch.domain.entities.MenuItem;
import app.jabafood.cleanarch.domain.enums.CuisineType;
import app.jabafood.cleanarch.domain.enums.UserType;
import app.jabafood.cleanarch.domain.useCases.menuItem.ListMenuItemsByRestaurantUseCase;
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
@Transactional
public class ListMenuItemsByRestaurantIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MenuItemJpaRepository menuItemJpaRepository;

    @Autowired
    private RestaurantJpaRepository restaurantJpaRepository;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private ListMenuItemsByRestaurantUseCase listMenuItemsByRestaurantUseCase;

    private String url;

    private UUID restaurantId;

    @BeforeEach
    void setup() {
        url = "/api/v1/menu-items/list/restaurant/{restaurantId}";

        menuItemJpaRepository.deleteAll();
        restaurantJpaRepository.deleteAll();
        userJpaRepository.deleteAll();

        UserEntity owner = new UserEntity(null, "John Doe", "john.doe@example.com", "johndoe", "123456", UserType.RESTAURANT_OWNER,
                                          new AddressEntity(null, "Rua Fake", "São Paulo", "SP", "00000-000", "Brazil", LocalDateTime.now()));
        userJpaRepository.save(owner);

        RestaurantEntity restaurant = new RestaurantEntity(null, "Sabor Italiano",
                                                           new AddressEntity(null, "Rua Fake", "São Paulo", "SP", "00000-000", "Brazil", LocalDateTime.now()), CuisineType.JAPANESE, LocalTime.of(18, 0), LocalTime.of(23, 0), owner, null, LocalDateTime.now());
        restaurantJpaRepository.save(restaurant);

        restaurantId = restaurant.getId();

        MenuItemEntity menuItem1 = new MenuItemEntity();
        menuItem1.setName("Pizza Margherita");
        menuItem1.setDescription("Delicious pizza with tomato, mozzarella, and basil");
        menuItem1.setPrice(BigDecimal.valueOf(12.99));
        menuItem1.setImagePath("/images/burger.png");
        menuItem1.setInRestaurantOnly(true);
        menuItem1.setRestaurant(restaurant);

        MenuItemEntity menuItem2 = new MenuItemEntity();
        menuItem2.setName("Filé ao molho madeira");
        menuItem2.setDescription("Delicious filé assado ao molhop madeira");
        menuItem2.setPrice(BigDecimal.valueOf(102.99));
        menuItem2.setImagePath("/images/burger.png");
        menuItem2.setInRestaurantOnly(true);
        menuItem2.setRestaurant(restaurant);

        menuItemJpaRepository.saveAll(List.of(menuItem1, menuItem2));
    }

    @Test
    void shouldRetrieveMenuItemByIdThroughController() throws Exception {
        mockMvc.perform(get(url, restaurantId)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Pizza Margherita"))
                .andExpect(jsonPath("$[1].name").value("Filé ao molho madeira"));
    }

    @Test
    void shouldRetrieveMenuItemByIdThroughUseCase() {
        List<MenuItem> menuItems = listMenuItemsByRestaurantUseCase.execute(restaurantId);

        assertThat(menuItems).isNotEmpty();
        assertThat(menuItems).hasSize(2);
        assertThat(menuItems.get(0)
                           .getName()).isEqualTo("Pizza Margherita");
        assertThat(menuItems.get(1)
                           .getName()).isEqualTo("Filé ao molho madeira");
    }

    @Test
    void shouldReturnNotFoundWhenMenuItemDoesNotExist() throws Exception {
        UUID nonExistentId = UUID.randomUUID();

        mockMvc.perform(get(url, nonExistentId)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}

