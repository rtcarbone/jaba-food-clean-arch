package app.jabafood.cleanarch.domain.entities;

import app.jabafood.cleanarch.domain.enums.UserType;
import app.jabafood.cleanarch.domain.exceptions.UserMandatoryFieldException;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
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
    String login;
    String password;
    UserType userType;
    LocalDateTime createdAt;
    Address address;

    public User copyWith(String name, String email, String login, String password, UserType userType, Address address) {
        return new User(
                this.id,
                name != null ? name : this.name,
                email != null ? email : this.email,
                login != null ? login : this.login,
                password != null ? password : this.password,
                userType != null ? userType : this.userType,
                LocalDateTime.now(),
                address != null ? address : this.address
        );
    }

    public void validate() {
        if (name == null || name.trim()
                .isEmpty()) {
            throw new UserMandatoryFieldException("name");
        }
        if (address == null) {
            throw new UserMandatoryFieldException("address");
        } else {
            address.validate();
        }
        if (login == null) {
            throw new UserMandatoryFieldException("login");
        }
        if (email == null) {
            throw new UserMandatoryFieldException("email");
        }
        if (password == null) {
            throw new UserMandatoryFieldException("password");
        }
        if (userType == null) {
            throw new UserMandatoryFieldException("userType");
        }
    }

}
