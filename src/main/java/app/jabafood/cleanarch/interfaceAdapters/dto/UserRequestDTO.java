package app.jabafood.cleanarch.interfaceAdapters.dto;

import app.jabafood.cleanarch.domain.enums.UserType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.UUID;

public record UserRequestDTO(
        @NotBlank(message = "Name cannot be empty")
        String name,

        @Email(message = "Invalid email format")
        @NotBlank(message = "Email cannot be empty")
        String email,

        @NotBlank(message = "Login cannot be empty")
        String login,

        @NonNull
        @Schema(description = "Password of the user", example = "password123")
        String password,

        @NotNull(message = "Address is required")
        @Schema(description = "Address of the user")
        AddressDTO address
) implements Serializable {
}