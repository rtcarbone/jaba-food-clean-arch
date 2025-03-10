package app.jabafood.cleanarch.domain.entities;

import app.jabafood.cleanarch.domain.enums.CuisineType;
import app.jabafood.cleanarch.domain.exceptions.InvalidClosingTimeException;
import app.jabafood.cleanarch.domain.exceptions.RestaurantMandatoryFieldException;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Getter
@EqualsAndHashCode
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor
public class Restaurant {
    UUID id;
    String name;
    Address address;
    CuisineType cuisineType;
    LocalTime openingTime;
    LocalTime closingTime;
    User owner;

    public Restaurant copyWith(String name, Address address, CuisineType cuisineType,
                               LocalTime openingTime, LocalTime closingTime, User owner) {
        return new Restaurant(
                this.id,
                name,
                address != null ? address : this.address,
                cuisineType != null ? cuisineType : this.cuisineType,
                openingTime != null ? openingTime : this.openingTime,
                closingTime != null ? closingTime : this.closingTime,
                owner != null ? owner : this.owner
        );
    }

    public void validate() {
        if (name == null || name.trim()
                .isEmpty()) {
            throw new RestaurantMandatoryFieldException("name");
        }
        if (address == null) {
            throw new RestaurantMandatoryFieldException("address");
        } else {
            address.validate();
        }
        if (cuisineType == null) {
            throw new RestaurantMandatoryFieldException("cuisineType");
        }
        if (openingTime == null) {
            throw new RestaurantMandatoryFieldException("openingTime");
        }
        if (closingTime == null) {
            throw new RestaurantMandatoryFieldException("closingTime");
        }
        if (openingTime.isAfter(closingTime) || openingTime.equals(closingTime)) {
            throw new InvalidClosingTimeException();
        }
        if (owner == null || owner.getId() == null) {
            throw new RestaurantMandatoryFieldException("owner");
        }
    }
}
