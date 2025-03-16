package app.jabafood.cleanarch.integration.user;

import app.jabafood.cleanarch.domain.entities.User;
import app.jabafood.cleanarch.domain.enums.UserType;
import app.jabafood.cleanarch.domain.useCases.user.GetUserByIdUseCase;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class GetUserByIdIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private GetUserByIdUseCase getUserByIdUseCase;

    private String url;

    private UUID userId;

    @BeforeEach
    void setup() {
        url = "/api/v1/users/{id}";

        userJpaRepository.deleteAll();

        UserEntity user = new UserEntity(null, "John Doe", "johndoe", "john@example.com", "password", UserType.RESTAURANT_OWNER, new AddressEntity(null, "Rua Fake", "São Paulo", "SP", "00000-000", "Brazil", null));
        userJpaRepository.save(user);

        userId = user.getId();
    }

    @Test
    void shouldRetrieveRestaurantByIdThroughController() throws Exception {
        // Realiza a requisição GET para o endpoint da API
        mockMvc.perform(get(url, userId)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId.toString()))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void shouldRetrieveUserByIdThroughUseCase() {
        // Usa o use case diretamente
        User user = getUserByIdUseCase.execute(userId);

        // Valida que os dados retornados estão corretos
        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(userId);
        assertThat(user.getName()).isEqualTo("John Doe");
    }

    @Test
    void shouldReturnNotFoundWhenUserDoesNotExist() throws Exception {
        UUID nonExistentId = UUID.randomUUID();

        // Realiza a requisição GET para um ID inexistente
        mockMvc.perform(get(url, nonExistentId)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
