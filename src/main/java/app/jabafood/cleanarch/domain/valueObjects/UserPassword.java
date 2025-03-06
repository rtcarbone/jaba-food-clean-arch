package app.jabafood.cleanarch.domain.valueObjects;

import app.jabafood.cleanarch.domain.exceptions.MissingPasswordException;
import app.jabafood.cleanarch.domain.exceptions.PasswordNotMatchException;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.util.StringUtils;

import static lombok.AccessLevel.PRIVATE;

@Getter
@EqualsAndHashCode
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor
public class UserPassword {
    String oldPassword;
    String newPassword;
    String repeatNewPassword;

    public void validate() {
        if (!StringUtils.hasText(oldPassword)) {
            throw new MissingPasswordException("Old password is mandatory");
        }

        if (!StringUtils.hasText(newPassword)) {
            throw new MissingPasswordException("New password is mandatory");
        }

        if (!StringUtils.hasText(repeatNewPassword)) {
            throw new MissingPasswordException("Repeat new password is mandatory");
        }

        if (!newPassword.equals(repeatNewPassword)) {
            throw new PasswordNotMatchException("New password and repeat new password do not match");
        }
    }
}



