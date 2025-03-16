package app.jabafood.cleanarch.integration.user;

import app.jabafood.cleanarch.application.dto.AddressRequestDTO;
import app.jabafood.cleanarch.application.dto.UserRequestDTO;
import app.jabafood.cleanarch.domain.entities.Address;
import app.jabafood.cleanarch.domain.entities.User;
import app.jabafood.cleanarch.domain.enums.UserType;
import app.jabafood.cleanarch.domain.useCases.user.CreateUserUseCase;
import app.jabafood.cleanarch.infrastructure.persistence.repositories.UserJpaRepository;
import com.fasterxml.jackson.databind.JsonNode;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
class CreateUserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private CreateUserUseCase createUserUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    private String url;

    @BeforeEach
    void setup() {
        url = "/api/v1/users/create";
        userJpaRepository.deleteAll();
    }

    @Test
    void shouldCreateUserSuccessfullyThroughController() throws Exception {
        UserRequestDTO user = new UserRequestDTO("Jane Doe", "jane@example.com", "janedoe", "password", UserType.CUSTOMER, new AddressRequestDTO("Rua Exemplo", "Rio de Janeiro", "RJ", "12345-678", "Brazil"));

        String userJson = objectMapper.writeValueAsString(user);

        MvcResult result = mockMvc.perform(post(url)
                                                   .contentType(MediaType.APPLICATION_JSON)
                                                   .content(userJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Jane Doe"))
                .andExpect(jsonPath("$.email").value("jane@example.com"))
                .andExpect(jsonPath("$.userType").value("CUSTOMER"))
                .andReturn();

        String jsonResponse = result.getResponse()
                .getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);
        String userId = jsonNode.get("id")
                .asText();

        assertThat(userId).isNotEmpty();
    }

    @Test
    void shouldCreateUserSuccessfullyThroughUseCase() {
        User user = new User(
                null,
                "Jane Doe",
                "jane@example.com",
                "janedoe",
                "password",
                UserType.CUSTOMER,
                null,
                new Address(null, "Rua Exemplo", "Rio de Janeiro", "RJ", "12345-678", "Brazil")
        );

        User createdUser = createUserUseCase.execute(user);

        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getId()).isNotNull();
        assertThat(createdUser.getName()).isEqualTo("Jane Doe");
        assertThat(createdUser.getUserType()).isEqualTo(UserType.CUSTOMER);
    }

    @Test
    void shouldReturnBadRequestWhenMissingRequiredFields() throws Exception {
        String invalidUserJson = """
                {
                    "email": "invalid@example.com",
                    "password": "password"
                }
                """;

        mockMvc.perform(post(url)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(invalidUserJson))
                .andExpect(status().isBadRequest());
    }
}

