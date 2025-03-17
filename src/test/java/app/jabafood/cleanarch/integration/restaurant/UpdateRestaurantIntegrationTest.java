package app.jabafood.cleanarch.integration.restaurant;

import app.jabafood.cleanarch.domain.entities.Restaurant;
import app.jabafood.cleanarch.domain.entities.User;
import app.jabafood.cleanarch.domain.enums.CuisineType;
import app.jabafood.cleanarch.domain.enums.UserType;
import app.jabafood.cleanarch.domain.useCases.restaurant.UpdateRestaurantUseCase;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
class UpdateRestaurantIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestaurantJpaRepository restaurantJpaRepository;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private UpdateRestaurantUseCase updateRestaurantUseCase;

    private UUID restaurantId;

    private String url;

    @BeforeEach
    void setup() {
        url = "/api/v1/restaurants/{id}/update";

        restaurantJpaRepository.deleteAll();
        userJpaRepository.deleteAll();

        UserEntity owner = new UserEntity(null, "John Doe", "johndoe", "john@example.com", "password", UserType.RESTAURANT_OWNER, new AddressEntity(null, "Rua Fake", "São Paulo", "SP", "00000-000", "Brazil", null));
        userJpaRepository.save(owner);

        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setName("Pizza Express");
        restaurantEntity.setAddress(new AddressEntity(null, "Rua Fake", "São Paulo", "SP", "00000-000", "Brazil", null));
        restaurantEntity.setCuisineType(CuisineType.PIZZERIA);
        restaurantEntity.setOpeningTime(LocalTime.of(10, 0));
        restaurantEntity.setClosingTime(LocalTime.of(22, 0));
        restaurantEntity.setOwner(owner);
        restaurantJpaRepository.save(restaurantEntity);

        restaurantId = restaurantEntity.getId();
    }

    @Test
    void shouldUpdateRestaurantSuccessfullyThroughController() throws Exception {
        String updatedRestaurantJson = """
                    {
                        "name": "Updated Name",
                        "cuisineType": "ITALIAN",
                        "openingTime": "09:00",
                        "closingTime": "23:00"
                    }
                """;

        mockMvc.perform(put(url, restaurantId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(updatedRestaurantJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"))
                .andExpect(jsonPath("$.cuisineType").value("ITALIAN"))
                .andExpect(jsonPath("$.openingTime").value("09:00"))
                .andExpect(jsonPath("$.closingTime").value("23:00"));
    }

    @Test
    void shouldUpdateRestaurantSuccessfullyThroughUseCase() {
        User owner = new User(UUID.randomUUID(), "John Doe", "johndoe", "john@example.com", "password", UserType.RESTAURANT_OWNER, null, null);
        Restaurant updatedRestaurant = new Restaurant(
                restaurantId, "Updated Name", null, CuisineType.ITALIAN,
                LocalTime.of(9, 0), LocalTime.of(23, 0), owner
        );

        Restaurant result = updateRestaurantUseCase.execute(restaurantId, updatedRestaurant);

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
                        "openingTime": "08:00",
                        "closingTime": "22:00"
                    }
                """;

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
                        "cuisineType": "BURGER",
                        "openingTime": "22:00:00",
                        "closingTime": "10:00:00"
                    }
                """;

        mockMvc.perform(put(url, restaurantId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(invalidRestaurantJson))
                .andExpect(status().isBadRequest());
    }
}
