package app.jabafood.cleanarch.integration.menuItem;

import app.jabafood.cleanarch.domain.entities.MenuItem;
import app.jabafood.cleanarch.domain.enums.CuisineType;
import app.jabafood.cleanarch.domain.useCases.menuItem.GetMenuItemByIdUseCase;
import app.jabafood.cleanarch.infrastructure.persistence.entities.AddressEntity;
import app.jabafood.cleanarch.infrastructure.persistence.entities.MenuItemEntity;
import app.jabafood.cleanarch.infrastructure.persistence.entities.RestaurantEntity;
import app.jabafood.cleanarch.infrastructure.persistence.repositories.MenuItemJpaRepository;
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
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
public class GetMenuItemByIdIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MenuItemJpaRepository menuItemJpaRepository;

    @Autowired
    private GetMenuItemByIdUseCase getMenuItemByIdUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    private String url;

    private UUID menuItemId;
    @Autowired
    private RestaurantJpaRepository restaurantJpaRepository;

    @BeforeEach
    void setup() {
        url = "/api/v1/menu-items/{id}";

        menuItemJpaRepository.deleteAll();
        restaurantJpaRepository.deleteAll();

        // Criando um restaurante
        RestaurantEntity restaurant = new RestaurantEntity(null, "Sabor Italiano",
                new AddressEntity(null, "Rua Fake", "São Paulo", "SP", "00000-000", "Brazil", null), CuisineType.JAPANESE, LocalTime.of(18, 0), LocalTime.of(23, 0), null, null, null);
        restaurantJpaRepository.save(restaurant);

        // Criando um item de menu para teste
        MenuItemEntity menuItemEntity = new MenuItemEntity();
        menuItemEntity.setName("Pizza Margherita");
        menuItemEntity.setDescription("Delicious pizza with tomato, mozzarella, and basil");
        menuItemEntity.setPrice(BigDecimal.valueOf(12.99));
        menuItemEntity.setImagePath("/images/burger.png");
        menuItemEntity.setInRestaurantOnly(true);
        menuItemEntity.setRestaurant(restaurant);
        menuItemJpaRepository.save(menuItemEntity);
        menuItemId = menuItemEntity.getId();
    }

    @Test
    void shouldRetrieveMenuItemByIdThroughController() throws Exception {
        mockMvc.perform(get(url, menuItemId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(menuItemId.toString()))
                .andExpect(jsonPath("$.name").value("Pizza Margherita"));
    }

    @Test
    void shouldRetrieveMenuItemByIdThroughUseCase() {
        MenuItem menuItem = getMenuItemByIdUseCase.execute(menuItemId);

        assertThat(menuItem).isNotNull();
        assertThat(menuItem.getId()).isEqualTo(menuItemId);
        assertThat(menuItem.getName()).isEqualTo("Pizza Margherita");
    }

    @Test
    void shouldReturnNotFoundWhenMenuItemDoesNotExist() throws Exception {
        UUID nonExistentId = UUID.randomUUID();

        mockMvc.perform(get(url, nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}

