package app.jabafood.cleanarch.integration.user;

import app.jabafood.cleanarch.domain.entities.User;
import app.jabafood.cleanarch.domain.enums.UserType;
import app.jabafood.cleanarch.domain.useCases.user.UpdateUserPasswordUseCase;
import app.jabafood.cleanarch.domain.valueObjects.UserPassword;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
class UpdateUserPasswordIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private UpdateUserPasswordUseCase updateUserPasswordUseCase;

    private UUID userId;
    private String url;

    @BeforeEach
    void setup() {
        url = "/api/v1/users/{id}/update-password";

        userJpaRepository.deleteAll();

        UserEntity user = new UserEntity(null, "John Doe", "johndoe", "john@example.com", "password", UserType.CUSTOMER,
                                         new AddressEntity(null, "Rua Fake", "São Paulo", "SP", "00000-000", "Brazil", null));
        userJpaRepository.save(user);

        userId = user.getId();
    }

    @Test
    void shouldUpdateUserPasswordSuccessfullyThroughController() throws Exception {
        String updateUserJson = """
                    {
                        "oldPassword": "password",
                        "newPassword": "newPassword",
                        "repeatNewPassword": "newPassword"
                    }
                """;

        mockMvc.perform(patch(url, userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(updateUserJson))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldUpdateUserPasswordSuccessfullyThroughUseCase() {
        UserPassword userPassword = new UserPassword("password", "newPassword", "newPassword");

        User userUpdated = updateUserPasswordUseCase.execute(userId, userPassword);

        assertThat(userUpdated).isNotNull();
        assertThat(userUpdated.getId()).isEqualTo(userId);
        assertThat(userUpdated.getPassword()).isEqualTo("newPassword");
    }

    @Test
    void shouldReturnNotFoundWhenUserDoesNotExist() throws Exception {
        UUID nonExistentUserId = UUID.randomUUID();

        String updateUserJson = """
                    {
                        "oldPassword": "password",
                        "newPassword": "newPassword",
                        "repeatNewPassword": "newPassword"
                    }
                """;

        mockMvc.perform(patch(url, nonExistentUserId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(updateUserJson))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User with ID '" + nonExistentUserId + "' not found."));
    }

    @Test
    void shouldReturnBadRequestWhenOldPasswordIsIncorrect() throws Exception {
        String updateUserJson = """
                    {
                        "oldPassword": "wrongPassword",
                        "newPassword": "newPassword",
                        "repeatNewPassword": "newPassword"
                    }
                """;

        mockMvc.perform(patch(url, userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(updateUserJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("The old password is invalid"));
    }

    @Test
    void shouldReturnBadRequestWhenNewPasswordsDoNotMatch() throws Exception {
        String updateUserJson = """
                    {
                        "oldPassword": "password",
                        "newPassword": "newPassword",
                        "repeatNewPassword": "differentPassword"
                    }
                """;

        mockMvc.perform(patch(url, userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(updateUserJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("New password and repeat new password do not match"));
    }
}
