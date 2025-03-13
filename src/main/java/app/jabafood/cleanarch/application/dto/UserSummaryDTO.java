package app.jabafood.cleanarch.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.UUID;

@Schema(description = "DTO for summarizing user details")
public record UserSummaryDTO(

        @Schema(description = "Unique identifier of the user",
                example = "550e8400-e29b-41d4-a716-446655440000",
                format = "uuid")
        UUID id,

        @Schema(description = "User login",
                example = "johndoe",
                maxLength = 100)
        String login

) implements Serializable {
}