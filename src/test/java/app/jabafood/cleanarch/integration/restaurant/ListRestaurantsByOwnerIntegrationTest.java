package app.jabafood.cleanarch.integration.restaurant;

import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.domain.enums.CuisineType;
import app.jabafood.cleanarch.domain.enums.UserType;
import app.jabafood.cleanarch.domain.useCases.restaurant.ListRestaurantsByOwnerUseCase;
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
class ListRestaurantsByOwnerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestaurantJpaRepository restaurantJpaRepository;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private ListRestaurantsByOwnerUseCase listRestaurantsByOwnerUseCase;

    private UUID ownerId;

    private String url;

    @BeforeEach
    void setup() {
        url = "/api/v1/restaurants/list/owner/";

        restaurantJpaRepository.deleteAll();
        userJpaRepository.deleteAll();

        UserEntity owner = new UserEntity(null, "John Doe", "johndoe", "john@example.com", "password", UserType.RESTAURANT_OWNER, new AddressEntity(null, "Rua Fake", "São Paulo", "SP", "00000-000", "Brazil", null));
        userJpaRepository.save(owner);
        ownerId = owner.getId();

        RestaurantEntity restaurant1 = new RestaurantEntity();
        restaurant1.setName("Pizza Express");
        restaurant1.setAddress(new AddressEntity(null, "Rua Fake", "São Paulo", "SP", "00000-000", "Brazil", null));
        restaurant1.setCuisineType(CuisineType.PIZZERIA);
        restaurant1.setOpeningTime(LocalTime.of(10, 0));
        restaurant1.setClosingTime(LocalTime.of(22, 0));
        restaurant1.setOwner(owner);

        RestaurantEntity restaurant2 = new RestaurantEntity();
        restaurant2.setName("Burger King");
        restaurant2.setAddress(new AddressEntity(null, "Rua Fake", "São Paulo", "SP", "00000-000", "Brazil", null));
        restaurant2.setCuisineType(CuisineType.BURGER);
        restaurant2.setOpeningTime(LocalTime.of(11, 0));
        restaurant2.setClosingTime(LocalTime.of(23, 0));
        restaurant2.setOwner(owner);

        restaurantJpaRepository.saveAll(List.of(restaurant1, restaurant2));
    }

    @Test
    void shouldListRestaurantsByOwnerSuccessfullyThroughController() throws Exception {
        mockMvc.perform(get(url + ownerId)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Pizza Express"))
                .andExpect(jsonPath("$[1].name").value("Burger King"));
    }

    @Test
    void shouldListRestaurantsByOwnerSuccessfullyThroughUseCase() {
        List<Restaurant> restaurants = listRestaurantsByOwnerUseCase.execute(ownerId);

        assertThat(restaurants).isNotEmpty();
        assertThat(restaurants).hasSize(2);
        assertThat(restaurants.get(0)
                           .getName()).isEqualTo("Pizza Express");
        assertThat(restaurants.get(1)
                           .getName()).isEqualTo("Burger King");
    }

    @Test
    void shouldReturnEmptyListWhenOwnerHasNoRestaurants() throws Exception {
        UserEntity newOwner = new UserEntity(null, "John Doe", "johndoe", "john@example.com", "password", UserType.RESTAURANT_OWNER, new AddressEntity(null, "Rua Fake", "São Paulo", "SP", "00000-000", "Brazil", null));
        userJpaRepository.save(newOwner);

        mockMvc.perform(get(url + newOwner.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));

        List<Restaurant> restaurants = listRestaurantsByOwnerUseCase.execute(newOwner.getId());

        assertThat(restaurants).isEmpty();
    }
}