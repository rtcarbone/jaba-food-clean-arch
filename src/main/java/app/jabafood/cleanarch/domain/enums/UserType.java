package app.jabafood.cleanarch.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserType {
    CUSTOMER("Customer"),
    RESTAURANT_OWNER("Restaurant Owner");

    private final String name;
}