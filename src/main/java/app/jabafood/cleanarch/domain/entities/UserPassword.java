package app.jabafood.cleanarch.domain.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Setter
@Getter
@EqualsAndHashCode
@FieldDefaults(level = PRIVATE)
@AllArgsConstructor
public class UserPassword {
  String oldPassword;
  String newPassword;
  String repeatNewPassword;
}



