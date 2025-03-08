package app.jabafood.cleanarch.interfaceAdapters.dto;

import java.io.Serializable;
import java.util.UUID;

public record UserSummaryDTO(
        UUID id,
        String username
) implements Serializable {
}