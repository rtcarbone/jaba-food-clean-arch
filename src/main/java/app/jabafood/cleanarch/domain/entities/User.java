package app.jabafood.cleanarch.domain.entities;

import app.jabafood.cleanarch.domain.enums.UserType;
import app.jabafood.cleanarch.domain.valueObjects.Address;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Getter
@EqualsAndHashCode
@FieldDefaults(level = PRIVATE)
public class User {

    final UUID id;
    final String name;
    final String username;
    final UserType userType;
    final Address address;
    String email;
    String password;
    LocalDateTime lastUpdate;

    public User(@NonNull String name, @NonNull String email, @NonNull String username,
                @NonNull String password, @NonNull UserType userType, @NonNull Address address) {

        this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.address = address;
        this.lastUpdate = LocalDateTime.now();
    }

    public User(@NonNull UUID id, @NonNull String name, @NonNull String email, @NonNull String username,
                @NonNull String password, @NonNull UserType userType, @NonNull Address address) {

        this.id = id;
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.address = address;
        this.lastUpdate = LocalDateTime.now();
    }

    public void updatePassword(@NonNull String newPassword) {
        // TODO VALIDACOES AO TROCAR SENHA
        this.password = newPassword;
        this.lastUpdate = LocalDateTime.now();
    }

    public void updateEmail(@NonNull String newEmail) {
        // TODO VALIDACOES AO TROCAR EMAIL
        this.email = newEmail;
        this.lastUpdate = LocalDateTime.now();
    }

}
