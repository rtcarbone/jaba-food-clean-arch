package app.jabafood.cleanarch.interfaceAdapters.dto;

import java.io.Serializable;
import java.util.UUID;

public record RestaurantSummaryDTO(
        UUID id,
        String name
) implements Serializable {
}
