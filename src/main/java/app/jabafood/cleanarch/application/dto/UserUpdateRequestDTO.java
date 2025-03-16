package app.jabafood.cleanarch.application.dto;

import app.jabafood.cleanarch.domain.enums.UserType;

import java.io.Serializable;

public record UserUpdateRequestDTO(
        String name,

        String email,

        UserType userType,

        String login,

        AddressRequestDTO address
) implements Serializable {
}

