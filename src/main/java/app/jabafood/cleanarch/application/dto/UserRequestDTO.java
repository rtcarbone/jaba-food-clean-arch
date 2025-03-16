package app.jabafood.cleanarch.application.dto;

import app.jabafood.cleanarch.domain.enums.UserType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

@Schema(description = "DTO for creating a User")
public record UserRequestDTO(

        @NotBlank(message = "Name cannot be empty")
        @Size(max = 255, message = "Name can have at most 255 characters")
        @Schema(description = "Full name of the user", example = "John Doe", maxLength = 255)
        String name,

        @Email(message = "Invalid email format")
        @NotBlank(message = "Email cannot be empty")
        @Schema(description = "Email of the user", example = "john.doe@example.com", format = "email")
        String email,

        @NotBlank(message = "Login cannot be empty")
        @Size(max = 50, message = "Login can have at most 50 characters")
        @Schema(description = "User login", example = "johndoe", maxLength = 50)
        String login,

        @NotBlank(message = "Password cannot be empty")
        @Size(min = 6, message = "Password must be at least 6 characters long")
        @Schema(description = "Password of the user", example = "password123", minLength = 6)
        String password,

        @NotNull(message = "User type cannot be null")
        @Schema(description = "Type of user", example = "RESTAURANT_OWNER", allowableValues = {"CUSTOMER", "RESTAURANT_OWNER"})
        UserType userType,

        @NotNull(message = "Address is required")
        @Schema(description = "Address of the user", implementation = AddressRequestDTO.class)
        AddressRequestDTO address

) implements Serializable {
}