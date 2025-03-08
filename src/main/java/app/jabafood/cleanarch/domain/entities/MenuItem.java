package app.jabafood.cleanarch.domain.entities;

import app.jabafood.cleanarch.domain.exceptions.MenuItemMandatoryFieldException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Getter
@EqualsAndHashCode
@FieldDefaults(level = PRIVATE, makeFinal = true)
@ToString(exclude = "restaurant")
public class MenuItem {
    UUID id;
    String name;
    String description;
    BigDecimal price;
    Boolean inRestaurantOnly;
    String imagePath;
    Restaurant restaurant;



    public MenuItem(UUID id, String name, String description, BigDecimal price,
                    Boolean inRestaurantOnly, String imagePath, Restaurant restaurant) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.inRestaurantOnly = inRestaurantOnly;
        this.imagePath = imagePath;
        this.restaurant = restaurant;

    }

    public MenuItem copyWith(String name, String description, BigDecimal price,
                             Boolean inRestaurantOnly, String imagePath, Restaurant restaurant){
        return new MenuItem(
                this.id,
                name != null ? name : this.name,
                description != null ? description : this.description,
                price != null ? price : this.price,
                inRestaurantOnly != null ? inRestaurantOnly : this.inRestaurantOnly,
                imagePath != null ? imagePath : this.imagePath,
                restaurant != null ? restaurant : this.restaurant

        );
    }

    public void validate() {
        if (name == null || name.trim().isEmpty()) {
            throw new MenuItemMandatoryFieldException("name");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new MenuItemMandatoryFieldException("description");
        }
        if (price == null) {
            throw new MenuItemMandatoryFieldException("price");
        }
        if (inRestaurantOnly == null) {
            throw new MenuItemMandatoryFieldException("inRestaurantOnly");
        }
        if (imagePath == null || imagePath.trim().isEmpty()) {
            throw new MenuItemMandatoryFieldException("imagePath");
        }

        if (restaurant == null) {
            throw new MenuItemMandatoryFieldException("restaurant_id");
        }
    }
}

