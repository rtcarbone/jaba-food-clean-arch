package app.jabafood.cleanarch.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CuisineType {
    PIZZERIA("Pizzaria"),
    BURGER("Hamburgueria"),
    ITALIAN("Italiana"),
    JAPANESE("Japonesa");

    private final String description;
}
