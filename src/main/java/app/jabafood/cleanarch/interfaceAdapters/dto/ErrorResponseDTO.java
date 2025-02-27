package app.jabafood.cleanarch.interfaceAdapters.dto;

public record ErrorResponseDTO(String message,
                               String method,
                               String path,
                               String timestamp,
                               int status) {
}

