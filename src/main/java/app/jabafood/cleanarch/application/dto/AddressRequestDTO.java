package app.jabafood.cleanarch.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

@Schema(description = "DTO for address details")
public record AddressRequestDTO(

        @NotBlank(message = "Street cannot be blank")
        @Size(max = 255, message = "Street name must be at most 255 characters")
        @Schema(description = "Street name", example = "123 Main St", maxLength = 255)
        String street,

        @NotBlank(message = "City cannot be blank")
        @Size(max = 255, message = "City name must be at most 255 characters")
        @Schema(description = "City name", example = "New York", maxLength = 255)
        String city,

        @NotBlank(message = "State cannot be blank")
        @Size(max = 100, message = "State name must be at most 100 characters")
        @Schema(description = "State name", example = "NY", maxLength = 100)
        String state,

        @NotBlank(message = "Zip Code cannot be blank")
        @Size(max = 20, message = "Zip Code must be at most 20 characters")
        @Schema(description = "Zip Code", example = "10001", maxLength = 20)
        String zipCode,

        @NotBlank(message = "Country cannot be blank")
        @Size(max = 100, message = "Country name must be at most 100 characters")
        @Schema(description = "Country name", example = "United States", maxLength = 100)
        String country

) implements Serializable {
}
