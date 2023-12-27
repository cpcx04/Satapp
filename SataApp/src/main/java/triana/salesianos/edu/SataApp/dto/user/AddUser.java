package triana.salesianos.edu.SataApp.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import triana.salesianos.edu.SataApp.validation.annotation.FieldsValueMatch;
import triana.salesianos.edu.SataApp.validation.annotation.UniqueUsername;

@FieldsValueMatch(
        field = "password",fieldMatch ="verifyPassword",
        message ="{addUser.password.nomatch}"
)
public record AddUser(
        @NotBlank(message = "{addUser.username.notblank}")
        @UniqueUsername
        String username,

        @NotBlank(message = "{addUser.fullname.notblank}")
        @Size(min = 3, message = "{addUser.fullname.size}")
        String fullName,
        @NotBlank(message = "{addUser.password.notblank}")
        @Size(min = 6, message = "{addUser.password.size}")
        String password,

        @NotBlank(message = "{addUser.verifyPassword.notblank}")
        String verifyPassword,
        @NotBlank(message = "{addUser.email.notblank}")
        @Email(message = "{addUser.email.email}")
        String email
){

}
