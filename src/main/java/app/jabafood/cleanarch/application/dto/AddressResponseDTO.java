package app.jabafood.cleanarch.application.dto;

import java.io.Serializable;
import java.util.UUID;

public record AddressResponseDTO(
        UUID id,
        String street,
        String city,
        String state,
        String zipCode,
        String country
) implements Serializable {
}
