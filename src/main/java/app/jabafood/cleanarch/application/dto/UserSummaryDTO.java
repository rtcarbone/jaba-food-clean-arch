package app.jabafood.cleanarch.application.dto;

import java.io.Serializable;
import java.util.UUID;

public record UserSummaryDTO(
        UUID id,
        String login
) implements Serializable {
}