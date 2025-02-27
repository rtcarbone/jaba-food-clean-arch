package app.jabafood.cleanarch.interfaceAdapters.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record AddressDTO(
        UUID id,

        @NotBlank(message = "Street cannot be blank")
        @Size(max = 255, message = "Street name is too long")
        String street,

        @NotBlank(message = "City cannot be blank")
        @Size(max = 255, message = "City name is too long")
        String city,

        @NotBlank(message = "State cannot be blank")
        @Size(max = 100, message = "State name is too long")
        String state,

        @NotBlank(message = "Zip Code cannot be blank")
        @Size(max = 20, message = "Zip Code is too long")
        String zipCode,

        @NotBlank(message = "Country cannot be blank")
        @Size(max = 100, message = "Country name is too long")
        String country
) {
}
