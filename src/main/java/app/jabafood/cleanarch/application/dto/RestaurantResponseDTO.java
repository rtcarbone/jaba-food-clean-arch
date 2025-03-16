package app.jabafood.cleanarch.application.dto;

import app.jabafood.cleanarch.domain.enums.CuisineType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.UUID;

public record RestaurantResponseDTO(
        UUID id,
        String name,
        AddressResponseDTO address,
        CuisineType cuisineType,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm") LocalTime openingTime,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm") LocalTime closingTime,
        UserSummaryDTO owner
) implements Serializable {
}