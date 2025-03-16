package app.jabafood.cleanarch.application.dto;

import app.jabafood.cleanarch.domain.enums.UserType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.UUID;

@Schema(description = "DTO for returning user details")
public record UserResponseDTO(

        @Schema(description = "Unique identifier of the user",
                example = "550e8400-e29b-41d4-a716-446655440000",
                format = "uuid")
        UUID id,

        @Schema(description = "Full name of the user",
                example = "John Doe",
                maxLength = 255)
        String name,

        @Schema(description = "Email of the user",
                example = "john.doe@example.com",
                format = "email")
        String email,

        @Schema(description = "User login",
                example = "johndoe",
                maxLength = 100)
        String login,

        @Schema(description = "Type of user",
                example = "CUSTOMER",
                allowableValues = {"CUSTOMER", "RESTAURANT_OWNER"})
        UserType userType,

        @Schema(description = "Address of the user",
                implementation = AddressResponseDTO.class)
        AddressResponseDTO address

) implements Serializable {
}