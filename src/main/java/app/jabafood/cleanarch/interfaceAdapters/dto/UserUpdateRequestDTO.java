package app.jabafood.cleanarch.interfaceAdapters.dto;

import app.jabafood.cleanarch.domain.enums.UserType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public record UserUpdateRequestDTO(
        @NotBlank(message = "Name cannot be empty")
        String name,

        @Email(message = "Invalid email format")
        @NotBlank(message = "Email cannot be empty")
        String email,

        UserType userType,

        @NotBlank(message = "Login cannot be empty")
        String login,

        @NotNull(message = "Address is required")
        @Schema(description = "Address of the user")
        AddressRequestDTO address
) implements Serializable {
}

