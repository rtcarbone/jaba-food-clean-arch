package app.jabafood.cleanarch.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.UUID;

@Schema(description = "DTO for returning address details")
public record AddressResponseDTO(

        @Schema(description = "Unique identifier of the address", example = "550e8400-e29b-41d4-a716-446655440000", format = "uuid")
        UUID id,

        @Schema(description = "Street name", example = "123 Main St", maxLength = 255)
        String street,

        @Schema(description = "City name", example = "New York", maxLength = 255)
        String city,

        @Schema(description = "State name", example = "NY", maxLength = 100)
        String state,

        @Schema(description = "Zip Code", example = "10001", maxLength = 20)
        String zipCode,

        @Schema(description = "Country name", example = "United States", maxLength = 100)
        String country

) implements Serializable {
}
