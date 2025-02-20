package app.jabafood.cleanarch.core.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserType {
    CUSTOMER("Cliente"),
    RESTAURANT_OWNER("Dono do Restaurante"),;

    private final String name;
}
