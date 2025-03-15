package app.jabafood.cleanarch.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

@Schema(description = "DTO for updating user password")
public record UserUpdatePasswordRequestDTO(

        @NotBlank(message = "Old password cannot be empty")
        @Schema(description = "Current password of the user", example = "oldPassword123")
        String oldPassword,

        @NotBlank(message = "New password cannot be empty")
        @Size(min = 6, message = "New password must be at least 6 characters long")
        @Schema(description = "New password for the user", example = "newSecurePassword123", minLength = 6)
        String newPassword,

        @NotBlank(message = "Repeat new password cannot be empty")
        @Size(min = 6, message = "Repeat new password must be at least 6 characters long")
        @Schema(description = "Confirmation of the new password", example = "newSecurePassword123", minLength = 6)
        String repeatNewPassword

) implements Serializable {
}