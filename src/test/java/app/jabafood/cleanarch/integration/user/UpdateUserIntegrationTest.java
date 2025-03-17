package app.jabafood.cleanarch.integration.user;

import app.jabafood.cleanarch.domain.entities.User;
import app.jabafood.cleanarch.domain.enums.UserType;
import app.jabafood.cleanarch.domain.useCases.user.UpdateUserUseCase;
import app.jabafood.cleanarch.infrastructure.persistence.entities.AddressEntity;
import app.jabafood.cleanarch.infrastructure.persistence.entities.UserEntity;
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
class UpdateUserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private UpdateUserUseCase updateUserUseCase;

    private UUID userId;
    private String url;

    @BeforeEach
    void setup() {
        url = "/api/v1/users/{id}/update";
        userJpaRepository.deleteAll();

        UserEntity user = new UserEntity(null, "John Doe", "johndoe", "john@example.com", "password", UserType.RESTAURANT_OWNER,
                                         new AddressEntity(null, "Rua Fake", "São Paulo", "SP", "00000-000", "Brazil", null));
        userJpaRepository.save(user);

        userId = user.getId();
    }

    @Test
    void shouldUpdateUserSuccessfullyThroughController() throws Exception {
        String updateUserJson = """
                    {
                        "name": "Updated Name",
                        "userType": "CUSTOMER"
                    }
                """;

        mockMvc.perform(put(url, userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(updateUserJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"))
                .andExpect(jsonPath("$.userType").value("CUSTOMER"));
    }

    @Test
    void shouldUpdateUserSuccessfullyThroughUseCase() {
        User updatedUser = new User(userId, "Updated Name", "johndoe", "john@example.com", "password", UserType.CUSTOMER, null, null);

        User result = updateUserUseCase.execute(userId, updatedUser);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(userId);
        assertThat(result.getName()).isEqualTo("Updated Name");
        assertThat(result.getUserType()).isEqualTo(UserType.CUSTOMER);
    }

    @Test
    void shouldReturnNotFoundWhenUserDoesNotExist() throws Exception {
        UUID nonExistentUserId = UUID.randomUUID();

        String updateUserJson = """
                    {
                        "name": "New Name",
                        "userType": "RESTAURANT_OWNER"
                    }
                """;

        mockMvc.perform(put(url, nonExistentUserId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(updateUserJson))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User with ID '" + nonExistentUserId + "' not found."));
    }

    @Test
    void shouldReturnBadRequestWhenUserTypeIsInvalid() throws Exception {
        String invalidUserJson = """
                    {
                        "name": "Invalid User",
                        "userType": "UNKNOWN_TYPE"
                    }
                """;

        mockMvc.perform(put(url, userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(invalidUserJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid enum value: Invalid value 'UNKNOWN_TYPE' for enum UserType. Accepted values: [CUSTOMER, RESTAURANT_OWNER]"));
    }

    @Test
    void shouldReturnBadRequestWhenNameIsEmpty() throws Exception {
        String invalidUserJson = """
                    {
                        "name": "",
                        "userType": "CUSTOMER"
                    }
                """;

        mockMvc.perform(put(url, userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(invalidUserJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("The field 'name' is mandatory for user registration."));
    }
}
