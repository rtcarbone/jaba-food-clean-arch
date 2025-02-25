package app.jabafood.cleanarch.domain.entities;

import app.jabafood.cleanarch.domain.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Getter
@EqualsAndHashCode
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor
public class User {
    UUID id;
    String name;
    String email;
    String username;
    String password;
    UserType userType;
    LocalDateTime createdAt;
    Address address;
    List<UUID> restaurants;

    public User copyWith(String name, String email, String password, Address address) {
        return new User(
                this.id,
                name != null ? name : this.name,
                email != null ? email : this.email,
                this.username,
                password != null ? password : this.password,
                this.userType,
                LocalDateTime.now(),
                address != null ? address : this.address,
                this.restaurants
        );
    }
}
