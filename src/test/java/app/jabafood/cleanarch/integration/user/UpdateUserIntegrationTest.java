package app.jabafood.cleanarch.integration.user;

import app.jabafood.cleanarch.JabaFoodCleanArchApplication;
import app.jabafood.cleanarch.domain.entities.User;
import app.jabafood.cleanarch.domain.enums.UserType;
import app.jabafood.cleanarch.infrastructure.persistence.entities.AddressEntity;
import app.jabafood.cleanarch.infrastructure.persistence.entities.UserEntity;
import app.jabafood.cleanarch.infrastructure.persistence.repositories.UserJpaRepository;
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

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = JabaFoodCleanArchApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UpdateUserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserJpaRepository userJpaRepository;

    private UUID userId;

    private String url;

    @BeforeEach
    void setup() {
        url = "/api/v1/users/{id}/update";
        userJpaRepository.deleteAll();
        UserEntity user = new UserEntity(null, "John Doe", "johndoe", "john@example.com", "password", UserType.RESTAURANT_OWNER, new AddressEntity(null, "Rua Fake", "São Paulo", "SP", "00000-000", "Brazil", null));
        userJpaRepository.save(user);
        userId = user.getId();

    }

    @Test
    void shouldUpdateUserSuccessfully() throws Exception {

        String updateUserJson = """
                    {
                        "name": "Updated Name",
                        "userType": "CUSTOMER"
                    }
                """;


        // Faz a requisição PUT para atualizar o usuário
        mockMvc.perform(put(url, userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateUserJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"))
                .andExpect(jsonPath("$.userType").value("CUSTOMER"));
    }
}

