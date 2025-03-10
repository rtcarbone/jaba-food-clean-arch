package app.jabafood.cleanarch.application.dto;

import java.io.Serializable;
import java.util.UUID;

public record RestaurantSummaryDTO(
        UUID id,
        String name
) implements Serializable {
}
