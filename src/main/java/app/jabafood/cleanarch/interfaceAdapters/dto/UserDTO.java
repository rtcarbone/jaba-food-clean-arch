package app.jabafood.cleanarch.interfaceAdapters.dto;

import app.jabafood.cleanarch.domain.enums.UserType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.UUID;

public record UserDTO(
        UUID id,

        @NotBlank(message = "Name cannot be empty")
        String name,

        @Email(message = "Invalid email format")
        @NotBlank(message = "Email cannot be empty")
        String email,

        @NotBlank(message = "Username cannot be empty")
        String username,

        @NotNull(message = "User type is required")
        UserType userType,

        @NonNull
        @Schema(description = "Password of the user", example = "password123", writeOnly = true)
        String password,

        @Schema(description = "Address of the user")
        AddressDTO address
) implements Serializable {
}