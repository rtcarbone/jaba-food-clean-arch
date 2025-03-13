package app.jabafood.cleanarch.application.dto;

public record ErrorResponseDTO(
        String message,
        String method,
        String path,
        String timestamp,
        int status) {
}

