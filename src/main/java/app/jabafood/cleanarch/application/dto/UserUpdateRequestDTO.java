package app.jabafood.cleanarch.application.dto;

import app.jabafood.cleanarch.domain.enums.UserType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

@Schema(description = "DTO for updating user details")
public record UserUpdateRequestDTO(

        @Size(max = 255, message = "Name must be at most 255 characters")
        @Schema(description = "Full name of the user", example = "John Doe", maxLength = 255)
        String name,

        @Schema(description = "Email of the user", example = "john.doe@example.com", format = "email")
        String email,

        @Schema(description = "Type of user", example = "CUSTOMER", allowableValues = {"CUSTOMER", "RESTAURANT_OWNER"})
        UserType userType,

        @Size(max = 50, message = "Login must be at most 50 characters")
        @Schema(description = "User login", example = "johndoe", maxLength = 50)
        String login,

        @Schema(description = "Address of the user", implementation = AddressRequestDTO.class)
        AddressRequestDTO address

) implements Serializable {
}