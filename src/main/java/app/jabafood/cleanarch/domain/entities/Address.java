package app.jabafood.cleanarch.domain.entities;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Getter
@EqualsAndHashCode
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor
public class Address {
    private final UUID id;
    private final String street;
    private final String city;
    private final String state;
    private final String zipCode;
    private final String country;

    public Address copyWith(String street, String city, String state, String zipCode, String country) {
        return new Address(
                this.id,
                street != null ? street : this.street,
                city != null ? city : this.city,
                state != null ? state : this.state,
                zipCode != null ? zipCode : this.zipCode,
                country != null ? country : this.country
        );
    }
}
