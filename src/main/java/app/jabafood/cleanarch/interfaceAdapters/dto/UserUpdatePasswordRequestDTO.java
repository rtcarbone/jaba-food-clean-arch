package app.jabafood.cleanarch.interfaceAdapters.dto;

public record UserUpdatePasswordRequestDTO(
        String oldPassword,
        String newPassword,
        String repeatNewPassword
) {

}
