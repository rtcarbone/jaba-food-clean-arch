package app.jabafood.cleanarch.application.dto;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public record UserUpdatePasswordRequestDTO(
        @NotBlank(message = "Old password cannot be empty") String oldPassword,
        @NotBlank(message = "New password cannot be empty") String newPassword,
        @NotBlank(message = "Repeat new password cannot be empty") String repeatNewPassword
) implements Serializable {
}

