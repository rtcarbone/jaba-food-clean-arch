package app.jabafood.cleanarch.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CuisineType {
    PIZZERIA("Pizzeria"),
    BURGER("Burger"),
    ITALIAN("Italian"),
    JAPANESE("Japanese");

    private final String description;
}