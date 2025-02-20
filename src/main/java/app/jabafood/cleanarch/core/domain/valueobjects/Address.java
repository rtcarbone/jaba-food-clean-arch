package app.jabafood.cleanarch.core.domain.valueobjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Getter
@EqualsAndHashCode
@FieldDefaults(level = PRIVATE)
public class Address {

    final String street;
    final Integer number;
    final String city;
    final String state;
    final String zipCode;

    public Address(@NonNull String street, @NonNull Integer number, @NonNull String city, @NonNull String state, @NonNull String zipCode) {
        this.street = street;
        this.number = number;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

}
