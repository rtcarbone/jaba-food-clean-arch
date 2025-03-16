package app.jabafood.cleanarch.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.UUID;

@Schema(description = "DTO for summarizing restaurant details")
public record RestaurantSummaryDTO(

        @Schema(description = "Unique identifier of the restaurant",
                example = "550e8400-e29b-41d4-a716-446655440000",
                format = "uuid")
        UUID id,

        @Schema(description = "Name of the restaurant",
                example = "Pasta House",
                maxLength = 255)
        String name

) implements Serializable {
}
