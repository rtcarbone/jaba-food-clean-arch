package app.jabafood.cleanarch.interfaceAdapters.dto;

import app.jabafood.cleanarch.domain.enums.UserType;

import java.io.Serializable;
import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String name,
        String email,
        String login,
        UserType userType,
        AddressResponseDTO address
) implements Serializable {
}