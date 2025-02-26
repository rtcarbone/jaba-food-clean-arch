package app.jabafood.cleanarch.domain.entities;

import app.jabafood.cleanarch.domain.enums.CuisineType;
import app.jabafood.cleanarch.domain.exceptions.InvalidClosingTimeException;
import app.jabafood.cleanarch.domain.exceptions.RestaurantMandatoryFieldException;
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
public class Restaurant {
    UUID id;
    String name;
    Address address;
    CuisineType cuisineType;
    LocalTime openingTime;
    LocalTime closingTime;
    UUID ownerId;
    List<UUID> menuItems;

    public Restaurant(UUID id, String name, Address address, CuisineType cuisineType,
                      LocalTime openingTime, LocalTime closingTime, UUID ownerId, List<UUID> menuItems) {
        validate(name, address, cuisineType, openingTime, closingTime, ownerId);
        this.id = id;
        this.name = name;
        this.address = address;
        this.cuisineType = cuisineType;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.ownerId = ownerId;
        this.menuItems = menuItems;
    }

    public Restaurant copyWith(String name, Address address, CuisineType cuisineType,
                               LocalTime openingTime, LocalTime closingTime) {
        validate(name, address, cuisineType, openingTime, closingTime, this.ownerId);
        return new Restaurant(
                this.id,
                name,
                address != null ? address : this.address,
                cuisineType != null ? cuisineType : this.cuisineType,
                openingTime != null ? openingTime : this.openingTime,
                closingTime != null ? closingTime : this.closingTime,
                this.ownerId,
                this.menuItems
        );
    }

    private void validate(String name, Address address, CuisineType cuisineType,
                          LocalTime openingTime, LocalTime closingTime, UUID ownerId) {
        if (name == null || name.trim()
                .isEmpty()) {
            throw new RestaurantMandatoryFieldException("name");
        }
        if (address == null) {
            throw new RestaurantMandatoryFieldException("address");
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
        if (ownerId == null) {
            throw new RestaurantMandatoryFieldException("owner");
        }
    }
}
