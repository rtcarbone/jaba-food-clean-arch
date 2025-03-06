package app.jabafood.cleanarch.domain.entities;

import app.jabafood.cleanarch.domain.exceptions.AddressMandatoryFieldException;
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
    UUID id;
    String street;
    String city;
    String state;
    String zipCode;
    String country;

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

    public void validate() {
        if (street == null || street.trim()
                .isEmpty()) {
            throw new AddressMandatoryFieldException("street");
        }
        if (city == null || city.trim()
                .isEmpty()) {
            throw new AddressMandatoryFieldException("city");
        }
        if (state == null || state.trim()
                .isEmpty()) {
            throw new AddressMandatoryFieldException("state");
        }
        if (zipCode == null || zipCode.trim()
                .isEmpty()) {
            throw new AddressMandatoryFieldException("zipCode");
        }
        if (country == null || country.trim()
                .isEmpty()) {
            throw new AddressMandatoryFieldException("country");
        }
    }
}
