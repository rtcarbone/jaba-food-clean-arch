package app.jabafood.cleanarch.integration.user;

import app.jabafood.cleanarch.domain.entities.User;
import app.jabafood.cleanarch.domain.enums.UserType;
import app.jabafood.cleanarch.domain.useCases.user.ListUsersUseCase;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
class ListUsersIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private ListUsersUseCase listUsersUseCase;

    private String url;

    @BeforeEach
    void setup() {
        url = "/api/v1/users/list";

        userJpaRepository.deleteAll();

        UserEntity user = new UserEntity(null, "John Doe", "johndoe", "john@example.com", "password", UserType.RESTAURANT_OWNER, new AddressEntity(null, "Rua Fake", "São Paulo", "SP", "00000-000", "Brazil", null));
        UserEntity user2 = new UserEntity(null, "John Doe 2", "johndoe2", "john2@example.com", "password", UserType.CUSTOMER, new AddressEntity(null, "Rua Fake", "São Paulo", "SP", "00000-000", "Brazil", null));

        userJpaRepository.saveAll(List.of(user, user2));
    }

    @Test
    void shouldListAllUsersSuccessfullyThroughController() throws Exception {
        mockMvc.perform(get(url)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[1].name").value("John Doe 2"));
    }

    @Test
    void shouldListAllUsersSuccessfullyThroughUseCase() {
        List<User> users = listUsersUseCase.execute();

        assertThat(users).isNotEmpty();
        assertThat(users).hasSize(2);
        assertThat(users.get(0)
                           .getName()).isEqualTo("John Doe");
        assertThat(users.get(1)
                           .getName()).isEqualTo("John Doe 2");
    }

    @Test
    void shouldReturnEmptyListWhenNoUsersExist() throws Exception {
        userJpaRepository.deleteAll();

        mockMvc.perform(get(url)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));

        List<User> users = listUsersUseCase.execute();
        assertThat(users).isEmpty();
    }
}